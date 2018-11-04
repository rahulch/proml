package proml.examples
import proml.Model
import proml.distributions.Continuous._
import proml.samplers.MetropolisHastings

object LinearRegression extends App {
  val trueA = -20d
  val trueB = 20d

  val points : Seq[(Double, Double)] = for {
    x <- -20 to 20
    noise = normal[Double](0,  1).get
  } yield (x*1.0,trueA * x + trueB + noise)

  case class Params(amu: Double, bmu: Double)

  val priorAssumption = Params(100,10)

  def getLogPrior(p: Params) : Double = {
    normal[Double](priorAssumption.amu, 1).logPdf(p.amu) +
    normal[Double](priorAssumption.bmu, 1).logPdf(p.bmu)
  }

  def getProposal(prior: Params) = for {
    a <- normal[Double](0, 1)
    b <- normal[Double](0,1)
  } yield Params(a + prior.amu, b + prior.bmu)

  val linearModel = new Model[Params, Double, Double]{
    override def proposal = (p: Params) => getProposal(p).get
    override def logPrior = getLogPrior
    override def model    = (p: Params) => (x: Double) => x * p.amu + p.bmu
    override def prior    = priorAssumption
  }
  val (best, _) = linearModel.fit(points, MetropolisHastings(20000, 15000))
  println(best)
}