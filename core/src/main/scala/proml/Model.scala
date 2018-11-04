package proml
import proml.samplers.Sampler

trait Model[P, X, Y]{

  def model : P => X => Y

  def logNormalLikelihood(param:P)(data: Seq[(X, Y)])(implicit cy: Backend[Y])  = data.map{case(x, y) =>
    import cy._
    ((model(param)(x) - y) * (model(param)(x) - y) / (-2.0 * data.size)).sum
  }.sum

  def prior: P

  def logNormalPrior(param: P)(implicit cp: Backend[P]) = {
    import cp._
    ((param - prior) * (param - prior) / -2.0).sum
  }

  def posterior(param:P)(data: Seq[(X, Y)])(implicit cy: Backend[Y], cp: Backend[P]) = {
    logNormalLikelihood(param)(data)(cy) + logNormalPrior(param)(cp)
  }

  def fit(data: Seq[(X, Y)], sampler: Sampler)(implicit cy: Backend[Y], cp: Backend[P]) = {
    sampler.sample(prior, posterior, data, cy, cp)
  }
}
