package proml.backend

trait MatrixOps[A] {

  import MatrixOps._

  def zeros[M, N](m: Int, n: Int): Matrix[A, M, N]
  def ones[M, N](m: Int, n: Int): Matrix[A, M, N]
  def rand[M, N](m: Int, n: Int): Matrix[A, M, N]
  def randNormal[M, N](m: Int, n: Int): Matrix[A, M, N]

  def transpose[M, N](lhs: Matrix[A, M, N]): Matrix[A, N, M]

  def mdot[M, N, O](lhs: Matrix[A, M, N], other: Matrix[A, N, O]): Matrix[A, M, O]
  def mpls[M, N](lhs: Matrix[A, M, N], other: Matrix[A, M, N]): Matrix[A, M, N]
  def mpls[M, N](lhs: Matrix[A, M, N], other: Double): Matrix[A, M, N]
  def mmul[M, N](lhs: Matrix[A, M, N], other: Matrix[A, M, N]): Matrix[A, M, N]
  def mmul[M, N](lhs: Matrix[A, M, N], other: Double): Matrix[A, M, N]
  def msub[M, N](lhs: Matrix[A, M, N], other: Matrix[A, M, N]): Matrix[A, M, N]
  def msub[M, N](lhs: Matrix[A, M, N], other: Double): Matrix[A, M, N]
  def mdiv[M, N](lhs: Matrix[A, M, N], other: Matrix[A, M, N]): Matrix[A, M, N]
  def mdiv[M, N](lhs: Matrix[A, M, N], other: Double): Matrix[A, M, N]
}

object MatrixOps {
  case class Matrix[A, M, N](underlying: A)
}
