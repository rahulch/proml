package proml
import proml.samplers.Sampler

trait Model[P, X, Y] {

  def model: P => X => Y

  def logNormalLikelihood(param: P)(data: Seq[(X, Y)])(implicit cy: Backend[Y]) =
    data.map {
      case (x, y) =>
        import cy._
        ((model(param)(x) - y) * (model(param)(x) - y) / (-2.0)).sum
    }.sum

  def prior: P

  def logPrior: P => Double

  def posterior(param: P)(data: Seq[(X, Y)])(implicit cy: Backend[Y]) = {
    logNormalLikelihood(param)(data)(cy) + logPrior(param)
  }

  def proposal: P => P

  def fit(data: Seq[(X, Y)], sampler: Sampler)(implicit cy: Backend[Y]) = {
    sampler.sample[P, X, Y](prior, posterior, data, proposal)
  }
}
