package proml.distributions

import proml.Backend

trait Continuous[A] extends Distribution[A] {
  def logPdf(a: A): Double
}

object Continuous {
  def normal[A](mu: A, sigma: A)(implicit c: Backend[A]): Continuous[A] = new Continuous[A] {
    import c._
    override def get                  = randNormal(mu) * sigma + mu
    override def logPdf(x: A): Double = (((x - mu) ^ 2d) / ((sigma ^ 2) * -2d)).sum
  }

  def uniform[A](low: A, high: A)(implicit c: Backend[A]): Continuous[A] = new Continuous[A] {
    import c._
    override def get                  = rand(low) * (high - low)
    override def logPdf(x: A): Double = -1d * log(high - low).sum
  }

  def chi2[A](n: Int, mu: A, sigma: A)(implicit c: Backend[A]) = {
    import c._
    normal[A](mu, sigma).map(x => x * x).repeat(n).map { _.total }
  }

  def studentsT[A](df: Int, mu: A, sigma: A)(implicit c: Backend[A]) = {
    import c._
    for {
      x <- normal[A](mu, sigma)
      y <- chi2[A](df, mu, sigma)
    } yield x * sqrt(divide(df, y))
  }

  def pareto[A](a: Double, xm: Double = 1.0, low: A, high: A)(implicit c: Backend[A]) = {
    import c._
    for {
      x <- uniform[A](low, high)
    } yield times(xm, power(x, -1 / a))
  }

  def exponential[A](l: Double, low: A, high: A)(implicit c: Backend[A]) = {
    import c._
    for {
      x <- uniform[A](low, high)
    } yield log(x) / (-l)
  }

  def laplace[A](b: Double, low: A, high: A)(implicit c: Backend[A]) = {
    import c._
    for {
      x <- exponential[A](1 / b, low, high)
      y <- exponential[A](1 / b, low, high)
    } yield x - y
  }

  def F[A](d1: Int, d2: Int, mu: A, sigma: A)(implicit c: Backend[A]) = {
    import c._
    for {
      x <- chi2[A](d1, mu, sigma)
      y <- chi2[A](d2, mu, sigma)
    } yield x / y
  }

  def lognormal[A](mu: A, sigma: A)(implicit c: Backend[A]) = {
    import c._
    for {
      z <- normal[A](mu, sigma)
    } yield exp(z)
  }

  def cauchy[A](mu: A, sigma: A)(implicit c: Backend[A]) = {
    import c._
    for {
      x <- normal[A](mu, sigma)
      y <- normal[A](mu, sigma)
    } yield x / y
  }

  def weibull[A](l: Double, k: Double, low: A, high: A)(implicit c: Backend[A]) = {
    import c._
    for {
      y <- exponential[A](1, low, high)
    } yield times(l, power(y, 1 / k))
  }
}
