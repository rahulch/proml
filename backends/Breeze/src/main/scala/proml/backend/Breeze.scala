package proml.backend

import breeze.linalg.DenseMatrix

import shapeless._
import shapeless.ops.nat._

object Breeze {

  implicit def breezeInstance = new MatrixOps[DenseMatrix[Double]] {
    override def zeros[M <: Nat, N <: Nat](implicit mToInt: ToInt[M], nToInt: ToInt[N]) = {
      Matrix[M, N](DenseMatrix.zeros[Double](mToInt.apply(), nToInt.apply()))
    }

    override def ones[M <: Nat, N <: Nat](implicit mToInt: ToInt[M], nToInt: ToInt[N]) = {
      Matrix[M, N](DenseMatrix.ones[Double](mToInt.apply(), nToInt.apply()))
    }

    override def transpose[M <: Nat, N <: Nat](underlying: DenseMatrix[Double]) = {
      Matrix[N, M](underlying.t)
    }
    override def mdot[M <: Nat, N <: Nat, O <: Nat](lhs: Matrix[M, N],
                                                    other: Matrix[N, O]): Matrix[M, O] = {
      Matrix[M, O](lhs dot other)
    }
    override def mpls[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Matrix[M, N]): Matrix[M, N] =
      Matrix[M, N](lhs.underlying + other.underlying)
    override def mpls[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Double): Matrix[M, N] =
      Matrix[M, N](lhs.underlying + other.underlying)
    override def mmul[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Matrix[M, N]): Matrix[M, N] =
      Matrix[M, N](lhs.underlying *:* other.underlying)
    override def mmul[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Double): Matrix[M, N] =
      Matrix[M, N](lhs.underlying *:* other.underlying)
    override def msub[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Matrix[M, N]): Matrix[M, N] =
      Matrix[M, N](lhs.underlying - other.underlying)
    override def msub[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Double): Matrix[M, N] =
      Matrix[M, N](lhs.underlying - other.underlying)
    override def mdiv[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Matrix[M, N]): Matrix[M, N] =
      Matrix[M, N](lhs.underlying /:/ other.underlying)
    override def mdiv[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Double): Matrix[M, N] =
      Matrix[M, N](lhs.underlying /:/ other.underlying)
  }
}
