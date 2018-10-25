package proml.distributions

import scala.annotation.tailrec
import scala.collection.parallel.ParSeq

trait Distribution[A] {
  self =>

  def get: A

  def map[B](f: A => B): Distribution[B] = new Distribution[B] {
    override def get = f(self.get)
  }

  def flatMap[B](f: A => Distribution[B]): Distribution[B] = new Distribution[B] {
    override def get: B = f(self.get).get
  }

  def filter(pred: A => Boolean): Distribution[A] = new Distribution[A] {
    @tailrec
    override def get: A = {
      val s: A = self.get
      if (pred(s)) s else this.get
    }
  }

  def withFilter(pred: A => Boolean): Distribution[A] = filter(pred)

  def given(pred: A => Boolean): Distribution[A] = filter(pred)

  def until(pred: List[A] => Boolean): Distribution[List[A]] = new Distribution[List[A]] {
    override def get: List[A] = {
      @tailrec
      def helper(sofar: List[A]): List[A] = {
        if (pred(sofar)) sofar

        else helper(self.get :: sofar)
      }
      helper(Nil)
    }
  }

  def sample(n: Int): List[A] = List.fill(n)(self.get)

  def samplePar(n: Int): ParSeq[A] = (0 until n).par.map(i => self.get)

  def zip[B](d: Distribution[B]): Distribution[(A, B)] = new Distribution[(A, B)] {
    override def get: (A, B) = (self.get, d.get)
  }

  def zipWith[B, C](d: Distribution[B])(f: (A, B) => C): Distribution[C] = new Distribution[C] {
    override def get = f(self.get, d.get)
  }

  def repeat(n: Int): Distribution[List[A]] = new Distribution[List[A]] {
    override def get: List[A] = self.sample(n)
  }
}