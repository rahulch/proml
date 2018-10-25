package proml.distributions

import proml.Backend

trait Continuous[A] extends Distribution[A]

object Continuous {
  def normal[A, B](mu: A, sigma: A)(implicit c: Backend[A, B]) = new Continuous[A] {
    import c._
    override def get = randNormal(mu) * sigma + mu
  }

  def uniform[A, B](low: A, high: A)(implicit c: Backend[A, B]) = new Continuous[A] {
    import c._
    override def get = rand(low) * (high - low)
  }

  def chi2[A, B](n: Int, mu: A, sigma: A)(implicit c: Backend[A, B])= {
    import c._
    normal[A, B](mu, sigma).map(x => x * x).repeat(n).map{_.total}
  }

  def studentsT[A, B](df: Int, mu: A, sigma: A)(implicit c: Backend[A, B])= {
    import c._
    for {
      x <- normal[A, B](mu, sigma)
      y <- chi2[A, B](df, mu, sigma)
    } yield x * sqrt(divide(df, y))
  }

  def pareto[A, B](a: Double, xm: Double = 1.0, low: A, high: A)(implicit c: Backend[A, B]) =  {
    import c._
    for {
      x <- uniform[A, B](low, high)
    } yield times(xm , power(x, -1/a))
  }

  def exponential[A, B](l: Double, low: A, high: A)(implicit c: Backend[A, B]) =  {
    import c._
    for {
      x <- uniform[A, B](low, high)
    } yield log(x) / (-l)
  }

  def laplace[A, B](b: Double, low: A, high: A)(implicit c: Backend[A, B]) =  {
    import c._
    for {
     x <-  exponential[A, B](1/b, low, high)
     y <-  exponential[A, B](1/b, low, high)
    } yield x - y
  }

  def F[A, B](d1: Int, d2: Int, mu: A, sigma: A)(implicit c: Backend[A, B])= {
    import c._
    for {
      x <-  chi2[A, B](d1, mu, sigma)
      y <-  chi2[A, B](d2, mu, sigma)
    } yield x / y
  }

  def lognormal[A, B](mu: A, sigma: A)(implicit c: Backend[A, B])= {
    import c._
    for {
      z <- normal[A, B](mu, sigma)
    } yield exp(z)
  }

  def cauchy[A, B](mu: A, sigma: A)(implicit c: Backend[A, B])= {
    import c._
    for {
      x <-  normal[A, B](mu, sigma)
      y <-  normal[A, B](mu, sigma)
    } yield x / y
  }

  def weibull[A, B](l: Double, k: Double, low: A, high: A)(implicit c: Backend[A, B]) =  {
    import c._
    for {
      y <- exponential[A, B](1, low, high)
    } yield times(l , power(y, 1/k))
  }
}
