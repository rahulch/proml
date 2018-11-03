package proml.backend

import breeze.numerics
import breeze.linalg._
import scala.util.Random
import proml.Backend

object Breeze{
  implicit val breezeInstance = new Backend[DenseMatrix[Double]]{
    override def plus(x: DenseMatrix[Double], y: DenseMatrix[Double]): DenseMatrix[Double] = x + y
    override def plus(x: DenseMatrix[Double], y: Double): DenseMatrix[Double] = x + y
    override def minus(x: DenseMatrix[Double], y: DenseMatrix[Double]): DenseMatrix[Double] = x - y
    override def minus(x: DenseMatrix[Double], y: Double): DenseMatrix[Double] = x - y
    override def times(x: DenseMatrix[Double], y: DenseMatrix[Double]): DenseMatrix[Double] = x * y
    override def times(x: DenseMatrix[Double], y: Double): DenseMatrix[Double] = x * y
    override def divide(x: DenseMatrix[Double], y: DenseMatrix[Double]): DenseMatrix[Double] = x / y
    override def divide(x: DenseMatrix[Double], y: Double): DenseMatrix[Double] = x / y
    override def divide(x: Double, y: DenseMatrix[Double]): DenseMatrix[Double] = x/y
    override def negate(x: DenseMatrix[Double]): DenseMatrix[Double] = -x
    override def rand(x: DenseMatrix[Double]): DenseMatrix[Double] = x map {_ => Random.nextDouble()}
    override def randNormal(x: DenseMatrix[Double]): DenseMatrix[Double] = x map {_ => Random.nextGaussian()}
    override def zero(x: DenseMatrix[Double]): DenseMatrix[Double] = x.zeroes()
    override def one(x: DenseMatrix[Double]): DenseMatrix[Double] = x.ones()
    override def power(x: DenseMatrix[Double], y: Double): DenseMatrix[Double] = numerics.pow(x, y)
    override def size(x: DenseMatrix[Double]): Long = x.size
    override def sumOnShape(x: DenseMatrix[Double]): Double = x.sum
    override def log(x: DenseMatrix[Double]): DenseMatrix[Double] = numerics.log(x)
    override def exp(x: DenseMatrix[Double]): DenseMatrix[Double] = numerics.exp(x)
    override def dot(x: DenseMatrix[Double], y: DenseMatrix[Double]): DenseMatrix[Double] = x * y
    override def transpose(x: DenseMatrix[Double]): DenseMatrix[Double] = x.t
  }
}
