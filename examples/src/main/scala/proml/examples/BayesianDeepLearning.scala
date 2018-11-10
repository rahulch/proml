package proml.examples

import breeze.linalg.DenseMatrix
import proml.distributions.Continuous._
import proml.Model
import proml.backend.Breeze, Breeze._
import proml.samplers.MetropolisHastings
import PlottingHelpers._
import com.cibo.evilplot.numeric.Point3d
import proml.backend.MatrixOps, MatrixOps.Matrix

object BayesianDeepLearning extends App {
  class _N
  class _2
  class _5
  class _1

  val (points1, points2) = makeMoons
  val input              = Matrix[DM, _N, _2](DenseMatrix(points1 ++ points2: _*))
  val output =
    Matrix[DM, _N, _1](DenseMatrix(Seq.fill(points1.size)(0.0) ++ Seq.fill(points2.size)(1.0): _*))

  scatterPlot(
    (points1 ++ points2).zip(Seq.fill(points1.size)(0.0) ++ Seq.fill(points2.size)(1.0)).map {
      case ((x, y), z) => Point3d[Double](x, y, z)
    })

  case class Params(w1: Matrix[DM, _2, _5], w2: Matrix[DM, _5, _5], w3: Matrix[DM, _5, _1])

  val ones = Params(
    Matrix[DM, _2, _5](DM1(2, 5)),
    Matrix[DM, _5, _5](DM1(5, 5)),
    Matrix[DM, _5, _1](DM1(5, 1)),
  )
  val zeros = Params(
    Matrix[DM, _2, _5](DM0(2, 5)),
    Matrix[DM, _5, _5](DM0(5, 5)),
    Matrix[DM, _5, _1](DM0(5, 1)),
  )
  val priorAssumption = zeros

  def getLogPrior(p: Params): Double = {
    import priorAssumption._
    normal[Matrix[DM, _2, _5]](w1, ones.w1).logPdf(p.w1) +
      normal[Matrix[DM, _5, _5]](w2, ones.w2).logPdf(p.w2) +
      normal[Matrix[DM, _5, _1]](w3, ones.w3).logPdf(p.w3)
  }

  def getProposal(prior: Params) =
    for {
      rn1 <- normal[Matrix[DM, _2, _5]](prior.w1, ones.w1)
      rn2 <- normal[Matrix[DM, _5, _5]](prior.w2, ones.w2)
      rn3 <- normal[Matrix[DM, _5, _1]](prior.w3, ones.w3)
    } yield Params(rn1, rn2, rn3)

  val neuralNetwork = new Model[Params, Matrix[DM, _N, _2], Matrix[DM, _N, _1]] {
    override def proposal = (p: Params) => getProposal(p).get
    override def logPrior = getLogPrior
    override def model =
      (p: Params) =>
        (x: Matrix[DM, _N, _2]) => {
          import p._
          import breezeInstance._
          msigmoid(mtanh(mtanh(x modot w1) modot w2) modot w3)
      }
    override def prior = priorAssumption
  }

  val (best, _) = neuralNetwork.fit(Seq((input, output)), MetropolisHastings(20000, 15000))
  println(best)

  val r = BigDecimal(-5).until(5, 0.1)
  val points = for {
    x <- r
    y <- r
  } yield {
    (x.toDouble, y.toDouble)
  }
  val predictions = {
    import breezeInstance._
    val testInput: Matrix[DM, _N, _2] = Matrix[DM, _N, _2](DenseMatrix(points: _*))
    msigmoid(mtanh(mtanh(testInput modot best.w1) modot best.w2) modot best.w3).underlying.toDenseVector.toArray
  }

  scatterPlot(points.zip(predictions).map { case ((x, y), z) => Point3d[Double](x, y, z) })
}
