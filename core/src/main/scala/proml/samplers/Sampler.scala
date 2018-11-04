package proml.samplers
import proml.Backend

trait Sampler {
  def sample[P, X, Y](
    implicit prior: P,
    posterior: P => Seq[(X, Y)] => Double,
    data: Seq[(X, Y)],
    cy: Backend[Y],
    cp: Backend[P]
  ): (P, Seq[P])
}
