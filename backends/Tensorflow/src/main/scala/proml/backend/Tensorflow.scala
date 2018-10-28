package proml.backend

import org.platanios.tensorflow.api.tensors.Tensor
import org.platanios.tensorflow.api._
import org.platanios.tensorflow.api.tensors.ops.Math._
import proml.Backend

object Tensorflow {
  type TypedTensor[A] = Tensor
  implicit val tensorInstance = new Backend[TypedTensor[Double]]{
    implicit def tensorToMathOps(value: tensors.Tensor): MathOps = MathOps(value)
    override def plus(x: TypedTensor[Double], y: TypedTensor[Double]): TypedTensor[Double] = x + y
    override def plus(x: TypedTensor[Double], y: Double): TypedTensor[Double] = x + y
    override def minus(x: TypedTensor[Double], y: TypedTensor[Double]): TypedTensor[Double] = x - y
    override def minus(x: TypedTensor[Double], y: Double): TypedTensor[Double] = x - y
    override def times(x: TypedTensor[Double], y: TypedTensor[Double]): TypedTensor[Double] = x * y
    override def times(x: TypedTensor[Double], y: Double): TypedTensor[Double] = x * y
    override def divide(x: TypedTensor[Double], y: TypedTensor[Double]): TypedTensor[Double] = x / y
    override def divide(x: TypedTensor[Double], y: Double): TypedTensor[Double] = x / y
    override def divide(x: Double, y: TypedTensor[Double]): TypedTensor[Double] = x/y
    override def negate(x: TypedTensor[Double]): TypedTensor[Double] = -x
    override def rand(x: TypedTensor[Double]): TypedTensor[Double] = Tensor.rand(x.dataType, x.shape)
    override def randNormal(x: TypedTensor[Double]): TypedTensor[Double] = Tensor.rand(x.dataType, x.shape)
    override def zero(x: TypedTensor[Double]): TypedTensor[Double] = Tensor.zeros(x.dataType, x.shape)
    override def one(x: TypedTensor[Double]): TypedTensor[Double] = Tensor.ones(x.dataType, x.shape)
    override def power(x: TypedTensor[Double], y: Double): TypedTensor[Double] = x ^ y
    override def size(x: TypedTensor[Double]): Long = x.size
    override def sumOnShape(x: TypedTensor[Double]): Double = x.sum(null, false).entriesIterator.next().asInstanceOf[Double]
    override def log(x: TypedTensor[Double]): TypedTensor[Double] = log1p(x)
    override def exp(x: TypedTensor[Double]): TypedTensor[Double] = expm1(x)
  }

  def toString(t: Tensor) = {
    val entries = t.entriesIterator.toList
    s"$t:$entries"
  }
}
