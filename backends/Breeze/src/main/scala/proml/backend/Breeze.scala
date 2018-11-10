package proml.backend

import breeze.linalg.DenseMatrix
import breeze.numerics._
import proml.backend.MatrixOps.Matrix

import scala.util.Random._

object Breeze {

  type DM = DenseMatrix[Double]

  def DM0(row: Int, column: Int) = DenseMatrix.zeros[Double](row, column)
  def DM1(row: Int, column: Int) = DenseMatrix.ones[Double](row, column)

  implicit val breezeInstance = new MatrixOps[DenseMatrix[Double]] {
    override def zeros[M, N](m: Int, n: Int) = {
      Matrix[DM, M, N](DenseMatrix.zeros[Double](m, n))
    }
    override def ones[M, N](m: Int, n: Int) = {
      Matrix[DM, M, N](DenseMatrix.ones[Double](m, n))
    }
    override def transpose[M, N](lhs: Matrix[DM, M, N]) =
      Matrix[DM, N, M](lhs.underlying.t)
    override def dotInner[M, N, O](lhs: Matrix[DM, M, N],
                                   other: Matrix[DM, N, O]): Matrix[DM, M, O] = {
      Matrix[DM, M, O](lhs.underlying * other.underlying)
    }
    override def plsInner[M, N](lhs: Matrix[DM, M, N], other: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying + other.underlying)
    override def plsInner[M, N](lhs: Matrix[DM, M, N], other: Double): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying + other)
    override def mulInner[M, N](lhs: Matrix[DM, M, N], other: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying *:* other.underlying)
    override def mulInner[M, N](lhs: Matrix[DM, M, N], other: Double): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying * other)
    override def subInner[M, N](lhs: Matrix[DM, M, N], other: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying - other.underlying)
    override def subInner[M, N](lhs: Matrix[DM, M, N], other: Double): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying - other)
    override def divInner[M, N](lhs: Matrix[DM, M, N], other: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying /:/ other.underlying)
    override def divInner[M, N](lhs: Matrix[DM, M, N], other: Double): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying /:/ other)
    override def rand[M, N](m: Int, n: Int): Matrix[DM, M, N] =
      Matrix[DM, M, N](ones(m, n).underlying.map { _ =>
        nextDouble()
      })
    override def randNormal[M, N](m: Int, n: Int): Matrix[DM, M, N] =
      Matrix[DM, M, N](ones(m, n).underlying.map { _ =>
        nextGaussian()
      })
    override def mtanh[M, N](lhs: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](tanh(lhs.underlying))
    override def msigmoid[M, N](lhs: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](sigmoid(lhs.underlying))
  }

  implicit def breezeBackend[M, N](implicit mo: MatrixOps[DM]) =
    new Backend[Matrix[DM, M, N]] {
      import mo._

      override def rand(x: Matrix[DM, M, N]): Matrix[DM, M, N] =
        mo.rand(x.underlying.rows, x.underlying.cols)
      override def randNormal(x: Matrix[DM, M, N]): Matrix[DM, M, N] =
        mo.randNormal(x.underlying.rows, x.underlying.cols)
      override def zero(x: Matrix[DM, M, N]): Matrix[DM, M, N] =
        mo.zeros(x.underlying.rows, x.underlying.cols)
      override def one(x: Matrix[DM, M, N]): Matrix[DM, M, N] =
        mo.ones(x.underlying.rows, x.underlying.cols)
      override def plus(x: Matrix[DM, M, N], y: Matrix[DM, M, N]): Matrix[DM, M, N] = plsInner(x, y)
      override def plus(x: Matrix[DM, M, N], y: Double): Matrix[DM, M, N]           = plsInner(x, y)
      override def minus(x: Matrix[DM, M, N], y: Matrix[DM, M, N]): Matrix[DM, M, N] =
        subInner(x, y)
      override def minus(x: Matrix[DM, M, N], y: Double): Matrix[DM, M, N] = subInner(x, y)
      override def times(x: Matrix[DM, M, N], y: Matrix[DM, M, N]): Matrix[DM, M, N] =
        mulInner(x, y)
      override def times(x: Matrix[DM, M, N], y: Double): Matrix[DM, M, N] = mulInner(x, y)
      override def divide(x: Matrix[DM, M, N], y: Matrix[DM, M, N]): Matrix[DM, M, N] =
        divInner(x, y)
      override def divide(x: Matrix[DM, M, N], y: Double): Matrix[DM, M, N] = divInner(x, y)
      override def power(x: Matrix[DM, M, N], y: Double): Matrix[DM, M, N] =
        Matrix[DM, M, N](pow(x.underlying, y))
      override def log(x: Matrix[DM, M, N]): Matrix[DM, M, N] =
        Matrix[DM, M, N](log1p(x.underlying))
      override def exp(x: Matrix[DM, M, N]): Matrix[DM, M, N] =
        Matrix[DM, M, N](expm1(x.underlying))
      override def size(x: Matrix[DM, M, N]): Long         = x.underlying.size
      override def sumOnShape(x: Matrix[DM, M, N]): Double = x.underlying.sum
    }
}
