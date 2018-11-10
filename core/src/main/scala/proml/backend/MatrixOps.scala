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
  def transpose[M <: Nat, N <: Nat](
    implicit
    mToInt: ToInt[M],
    nToInt: ToInt[N]
  ): Matrix[N, M]
  def dott[M <: Nat, N <: Nat, O <: Nat](other: Matrix[N, O])(
    implicit
    mToInt: ToInt[M],
    nToInt: ToInt[N],
    oToInt: ToInt[O],
  ): Matrix[M, O]

  def mplus[M <: Nat, N <: Nat](other: Double)(
    implicit
    mToInt: ToInt[M],
    nToInt: ToInt[N]
  ): Matrix[M, N]
  def mplus[M <: Nat, N <: Nat](other: Double)(
    implicit
    mToInt: ToInt[M],
    nToInt: ToInt[N]
  ): Matrix[M, N]
  def msub[M <: Nat, N <: Nat](other: Double)(
    implicit
    mToInt: ToInt[M],
    nToInt: ToInt[N]
  ): Matrix[M, N]
  def mdiv[M <: Nat, N <: Nat](other: Double)(
    implicit
    mToInt: ToInt[M],
    nToInt: ToInt[N]
  ): Matrix[M, N]

}
