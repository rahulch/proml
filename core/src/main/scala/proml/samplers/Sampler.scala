package proml.samplers

import proml.distributions.Distribution

trait Sampler {

  def fit[V, D](
     initial: V,
     distribution: V => Distribution[V],
     observations: Seq[D],
     cost: (V, Seq[D]) => Double,
     iterations: Int,
     iterationsToBurn: Int,
   ): (V, Seq[V])

}
