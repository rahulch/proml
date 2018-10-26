package proml.samplers

import proml.distributions.Distribution

object MetropolisHastings extends Sampler {
  def fit[V, D](
    initial: V,
    distribution: V => Distribution[V],
    observations: Seq[D],
    cost: (V, Seq[D]) => Double,
    iterations: Int = 20000,
    iterationsToBurn: Int = 10000,
  ): (V, Seq[V]) = {
    (0 to iterations).foldLeft(initial, Seq.empty[V]) { case ((current: V, samples: Seq[V]), iteration: Int) =>
      val proposal = distribution(current).get
      val decision = if (cost(proposal, observations) > cost(current, observations)) proposal else current
      (decision, if (iteration > iterationsToBurn) samples :+ proposal else Nil)
    }
  }
}
