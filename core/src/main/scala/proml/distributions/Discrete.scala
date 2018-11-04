package proml.distributions

import proml.distributions.Continuous._

import scala.annotation.tailrec

trait Discrete[A] extends Distribution[A]

object Discrete {

  type RNG = {
    def nextInt: Int => Int
  }

  def simpleUniform = uniform[Double](0, 1)
  def simpleNormal  = normal[Double](0, 1)

  sealed abstract class Coin
  case object H extends Coin
  case object T extends Coin
  def coin(implicit rand: RNG): Distribution[Coin] = discreteUniform(List(H, T))
  def biasedCoin(p: Double): Distribution[Coin]    = discrete(H -> p, T -> (1 - p))

  def d(n: Int)(implicit rand: RNG): Distribution[Int] = new Distribution[Int] {
    override def get = rand.nextInt(n) + 1
  }
  def die(implicit rand: RNG)          = d(6)
  def dice(n: Int)(implicit rand: RNG) = die.repeat(n)

  def tf(p: Double = 0.5) = discrete(true -> p, false -> (1 - p))

  def bernoulli(p: Double = 0.5) = discrete(1 -> p, 0 -> (1 - p))

  def discreteUniform[A](values: Iterable[A])(implicit rand: RNG): Distribution[A] =
    new Distribution[A] {
      private val vec  = Vector() ++ values
      override def get = vec(rand.nextInt(vec.length))
    }

  def discrete[A](weightedValues: (A, Double)*): Distribution[A] = new Distribution[A] {
    val len               = weightedValues.size
    val scale             = len / weightedValues.map(_._2).sum
    val scaled            = weightedValues.map { case (a, p) => (a, p * scale) }.toList
    val (smaller, bigger) = scaled.partition(_._2 < 1.0)

    // The alias method: http://www.keithschwarz.com/darts-dice-coins/
    @tailrec
    private def alias(smaller: List[(A, Double)],
                      bigger: List[(A, Double)],
                      rest: List[(A, Double, Option[A])]): List[(A, Double, Option[A])] = {
      (smaller, bigger) match {
        case ((s, sp) :: ss, (b, pb) :: bb) =>
          val remainder = (b, pb - (1.0 - sp))
          val newRest   = (s, sp, Some(b)) :: rest
          if (remainder._2 < 1)
            alias(remainder :: ss, bb, newRest)
          else
            alias(ss, remainder :: bb, newRest)
        case (_, (b, pb) :: bb) =>
          alias(smaller, bb, (b, 1.0, None) :: rest)
        case ((s, sp) :: ss, _) =>
          alias(ss, bigger, (s, 1.0, None) :: rest)
        case _ =>
          rest
      }
    }
    val table = Vector() ++ alias(smaller, bigger, Nil)
    private def select(p1: Double, p2: Double, table: Vector[(A, Double, Option[A])]): A = {
      table((p1 * len).toInt) match {
        case (a, _, None)    => a
        case (a, p, Some(b)) => if (p2 <= p) a else b
      }
    }
    override def get = {
      select(simpleUniform.get, simpleUniform.get, table)
    }
  }

  def geometric(p: Double): Distribution[Int] = {
    tf(p).until(_.headOption.contains(true)).map(_.size - 1)
  }

  def binomial(p: Double, n: Int): Distribution[Int] = {
    bernoulli(p).repeat(n).map(_.sum)
  }

  def negativeBinomial(p: Double, r: Int): Distribution[Int] = {
    tf(p).until(_.count(_ == false) == r).map(_.size - r)
  }

  def poisson(lambda: Double): Distribution[Int] = {
    exponential(1, 0d, 1d).until(_.sum > lambda).map(_.size - 1)
  }

  def zipf(s: Double, n: Int): Distribution[Int] = {
    discrete((1 to n).map(k => k -> 1.0 / math.pow(k, s)): _*)
  }
}
