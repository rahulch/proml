package proml.examples

import breeze.linalg.DenseMatrix
import breeze.numerics.{sigmoid, tanh}
import proml.distributions.Continuous._
import proml.Model
import proml.backend.Breeze._
import proml.samplers.MetropolisHastings

import PlottingHelpers._
import com.cibo.evilplot.numeric.Point3d

object BayesianDeepLearning extends App {

  val (points1, points2) = makeMoons
  val input              = DenseMatrix(points1 ++ points2: _*)
  val output             = DenseMatrix(Seq.fill(points1.size)(0.0) ++ Seq.fill(points2.size)(1.0): _*)

  scatterPlot(
    (points1 ++ points2).zip(Seq.fill(points1.size)(0.0) ++ Seq.fill(points2.size)(1.0)).map {
      case ((x, y), z) => Point3d[Double](x, y, z)
    })

  //TODO: Make the matrix operations type safe
  type DM = DenseMatrix[Double]
  case class Params(w1: DM, w2: DM, w3: DM)
  def DM0(row: Int, column: Int) = DenseMatrix.zeros[Double](row, column)
  def DM1(row: Int, column: Int) = DenseMatrix.ones[Double](row, column)
  val priorAssumption = Params(
    DM0(2, 5),
    DM0(5, 5),
    DM0(5, 1)
  )

  def getLogPrior(p: Params): Double = {
    normal[DM](priorAssumption.w1, DM1(2, 5)).logPdf(p.w1) +
      normal[DM](priorAssumption.w2, DM1(5, 5)).logPdf(p.w2) +
      normal[DM](priorAssumption.w3, DM1(5, 1)).logPdf(p.w3)
  }

  def getProposal(prior: Params) =
    for {
      rn1 <- normal[DM](prior.w1, DM1(2, 5))
      rn2 <- normal[DM](prior.w2, DM1(5, 5))
      rn3 <- normal[DM](prior.w3, DM1(5, 1))
    } yield Params(rn1, rn2, rn3)

  val neuralNetwork = new Model[Params, DM, DM] {
    override def proposal = (p: Params) => getProposal(p).get
    override def logPrior = getLogPrior
    override def model =
      (p: Params) =>
        (x: DM) => {
          import p._
          sigmoid(tanh(tanh(x * w1) * w2) * w3)
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
  val predictions = sigmoid(tanh(tanh(DenseMatrix(points: _*) * best.w1) * best.w2) * best.w3).toDenseVector.toArray

  scatterPlot(points.zip(predictions).map { case ((x, y), z) => Point3d[Double](x, y, z) })
}
