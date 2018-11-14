package proml.backend

import org.platanios.tensorflow.api.tensors.Tensor
import org.platanios.tensorflow.api._
import org.platanios.tensorflow.api.tensors.ops.Math._

import proml.backend.MatrixOps.Matrix

object Tensorflow {

  type DM = Tensor[Double]
  def DM0(row: Int, column: Int) = Tensor.zeros[Double](Shape(row, column))
  def DM1(row: Int, column: Int) = Tensor.ones[Double](Shape(row, column))

  implicit val breezeInstance = new MatrixOps[Tensor[Double]] {
    override def zeros[M, N](m: Int, n: Int) = {
      Matrix[DM, M, N](DM0(m, n))
    }
    override def ones[M, N](m: Int, n: Int) = {
      Matrix[DM, M, N](DM1(m, n))
    }
    override def transpose[M, N](lhs: Matrix[DM, M, N]) =
      Matrix[DM, N, M](lhs.underlying.transpose())
    override def dotInner[M, N, O](lhs: Matrix[DM, M, N],
                                   other: Matrix[DM, N, O]): Matrix[DM, M, O] = {
      Matrix[DM, M, O](lhs.underlying.tensorDot(other.underlying, 2))
    }
    override def plsInner[M, N](lhs: Matrix[DM, M, N], other: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying + other.underlying)
    override def plsInner[M, N](lhs: Matrix[DM, M, N], other: Double): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying + other)
    override def mulInner[M, N](lhs: Matrix[DM, M, N], other: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying * other.underlying)
    override def mulInner[M, N](lhs: Matrix[DM, M, N], other: Double): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying * other)
    override def subInner[M, N](lhs: Matrix[DM, M, N], other: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying - other.underlying)
    override def subInner[M, N](lhs: Matrix[DM, M, N], other: Double): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying - other)
    override def divInner[M, N](lhs: Matrix[DM, M, N], other: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying / other.underlying)
    override def divInner[M, N](lhs: Matrix[DM, M, N], other: Double): Matrix[DM, M, N] =
      Matrix[DM, M, N](lhs.underlying / other)
    override def rand[M, N](m: Int, n: Int): Matrix[DM, M, N] = {
      val ones = DM1(m, n)
      Matrix[DM, M, N](Tensor.rand(ones.dataType, ones.shape))
    }
    override def randNormal[M, N](m: Int, n: Int): Matrix[DM, M, N] = {
      val ones = DM1(m, n)
      Matrix[DM, M, N](Tensor.randn(ones.dataType, ones.shape))
    }
    override def mtanh[M, N](lhs: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](tanh(lhs.underlying))
    override def msigmoid[M, N](lhs: Matrix[DM, M, N]): Matrix[DM, M, N] =
      Matrix[DM, M, N](sigmoid(lhs.underlying))
  }

  implicit def tensorflowBackend[M, N](implicit mo: MatrixOps[DM]) =
    new Backend[Matrix[DM, M, N]] {
      import mo._
      implicit def rows(x: Matrix[DM, M, N]) = x.underlying.shape.size(0)
      implicit def cols(x: Matrix[DM, M, N]) = x.underlying.shape.size(1)
      override def rand(x: Matrix[DM, M, N]): Matrix[DM, M, N] =
        mo.rand(rows(x), cols(x))
      override def randNormal(x: Matrix[DM, M, N]): Matrix[DM, M, N] =
        mo.randNormal(rows(x), cols(x))
      override def zero(x: Matrix[DM, M, N]): Matrix[DM, M, N] =
        mo.zeros(rows(x), cols(x))
      override def one(x: Matrix[DM, M, N]): Matrix[DM, M, N] =
        mo.ones(rows(x), cols(x))
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
      override def sumOnShape(x: Matrix[DM, M, N]): Double = x.underlying.sum().entriesIterator.sum
    }
}
