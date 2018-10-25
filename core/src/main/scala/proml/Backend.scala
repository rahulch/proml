package proml

trait Backend[A, B] {
  def plus(x: A, y: A): A
  def plus(x: A, y: Double): A
  def plus(x: Double, y: A): A = plus(y, x)
  def minus(x: A, y: A): A
  def minus(x: A, y: Double): A
  def minus(x: Double, y: A): A = -minus(y, x)
  def times(x: A, y: A): A
  def times(x: A, y: Double): A
  def times(x: Double, y: A): A = times(y, x)
  def divide(x: A, y: A): A
  def divide(x: A, y: Double): A
  def divide(x: Double, y: A): A
  def negate(x: A): A
  def rand(x: A): A
  def randNormal(x: A): A
  def zero(x: A): A
  def one(x: A): A
  def power(x: A, y: Double): A
  def greater(x: A, y: A): B
  def greater(x: A, y: Double): B
  def greater(x: Double, y: A): B = not(greater(y, x))
  def sqrt(x: A): A = power(x, 0.5)
  def log(x: A): A
  def exp(x: A): A
  def not(x: B): B
  def size(x: A): Long
  def sumOnShape(x: A): Double

  class Ops(lhs: A) {
    def +(rhs: A): A = plus(lhs, rhs)
    def +(rhs: Double): A = plus(lhs, rhs)
    def -(rhs: A): A = minus(lhs, rhs)
    def -(rhs: Double): A = minus(lhs, rhs)
    def *(rhs: A): A = times(lhs, rhs)
    def *(rhs: Double): A  = times(lhs, rhs)
    def /(rhs: A): A = divide(lhs, rhs)
    def /(rhs: Double): A  = divide(lhs, rhs)
    def >(rhs: A): B = greater(lhs, rhs)
    def >(rhs: Double): B = greater(lhs, rhs)
    def <(rhs: A): B = not(greater(lhs, rhs))
    def <(rhs: Double): B = not(greater(lhs, rhs))
    def |>(rhs: A): Boolean = sumOnShape(lhs) > sumOnShape(rhs)
    def |>(rhs: Double): Boolean = sumOnShape(lhs) > rhs
    def <|(rhs: A): Boolean = sumOnShape(lhs) < sumOnShape(rhs)
    def <|(rhs: Double): Boolean = sumOnShape(lhs) < rhs
    def unary_-(): A = negate(lhs)
    def ones(): A = one(lhs)
    def zeroes(): A = zero(lhs)
    def sum: Double = sumOnShape(lhs)
  }

  class DoubleOps(lhs: Double) {
    def +(rhs: A): A = plus(lhs, rhs)
    def -(rhs: A): A = minus(lhs, rhs)
    def *(rhs: A): A = times(lhs, rhs)
    def /(rhs: A): A = divide(lhs, rhs)
    def >(rhs: A): B = greater(lhs, rhs)
    def <(rhs: A): B = greater(lhs, rhs)
  }

  implicit def mkDatatypeOps(lhs: A): Ops = new Ops(lhs)
  implicit def DoubleDatatypeOps(lhs: Double): DoubleOps = new DoubleOps(lhs)
  implicit class Aggregates(seq: Traversable[A]) {
    def total: A = seq.reduce(_ + _)
  }

}
