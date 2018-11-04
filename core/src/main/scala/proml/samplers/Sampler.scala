package proml.samplers

trait Sampler {
  def sample[P, X, Y](
    implicit prior: P,
    posterior: P => Seq[(X, Y)] => Double,
    data: Seq[(X, Y)],
    proposal: P => P
  ): (P, Seq[P])
}
