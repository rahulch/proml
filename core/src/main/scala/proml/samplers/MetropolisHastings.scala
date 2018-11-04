package proml.samplers

case class MetropolisHastings(iterations: Int, burnIterations: Int) extends Sampler {
  override def sample[P, X, Y](
    implicit prior: P,
    posterior: P => Seq[(X, Y)] => Double,
    data: Seq[(X, Y)],
    proposal: P => P
  ): (P, Seq[P]) = {
    (0 to iterations).foldLeft(prior, Seq.empty[P]) {
      case ((current, samples: Seq[P]), iteration: Int) =>
        val p      = proposal(current)
        val result = if (posterior(p)(data) >= posterior(current)(data)) p else current
        (result, if (iteration > burnIterations) samples :+ p else Nil)
    }
  }
}
