package proml.samplers
import proml.Backend

case class MetropolisHastings(iterations: Int, burnIterations: Int) extends Sampler {
  override def sample[P, X, Y](
    implicit prior: P,
    posterior: P => Seq[(X, Y)] => Double,
    data: Seq[(X, Y)],
    cy: Backend[Y],
    cp: Backend[P]
  ): (P, Seq[P]) =  {
    (0 to iterations).foldLeft(prior, Seq.empty[P]) {
      case ((current, samples: Seq[P]), iteration: Int) =>
        val n        = data.size
        val proposal = prior // TODO : Figure out a way to get proposals automatically
        val result   = if (posterior(proposal)(data) > posterior(current)(data)) proposal else current
        (result, if (iteration > burnIterations) samples :+ proposal else Nil)
    }
  }
}
