package proml.backend
import shapeless.Nat
import shapeless.ops.nat.ToInt

trait MatrixOps[A] {

  case class Matrix[M <: Nat, N <: Nat](underlying: A)

  def zeros[M <: Nat, N <: Nat](
    implicit
    mToInt: ToInt[M],
    nToInt: ToInt[N]
  ): Matrix[M, N]
  def ones[M <: Nat, N <: Nat](
    implicit
    mToInt: ToInt[M],
    nToInt: ToInt[N]
  ): Matrix[M, N]

  def transpose[M <: Nat, N <: Nat](lhs: Matrix[M, N]): Matrix[N, M]
  def mdot[M <: Nat, N <: Nat, O <: Nat](lhs: Matrix[M, N], other: Matrix[N, O]): Matrix[M, O]
  def mpls[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Matrix[M, N]): Matrix[M, N]
  def mpls[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Double): Matrix[M, N]
  def mmul[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Matrix[M, N]): Matrix[M, N]
  def mmul[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Double): Matrix[M, N]
  def msub[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Matrix[M, N]): Matrix[M, N]
  def msub[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Double): Matrix[M, N]
  def mdiv[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Matrix[M, N]): Matrix[M, N]
  def mdiv[M <: Nat, N <: Nat](lhs: Matrix[M, N], other: Double): Matrix[M, N]
}
