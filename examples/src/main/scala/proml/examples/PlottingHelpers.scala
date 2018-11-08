package proml.examples

import com.cibo.evilplot.displayPlot
import com.cibo.evilplot.plot.ScatterPlot
import com.cibo.evilplot.colors.{ContinuousColoring, HTMLNamedColors}
import com.cibo.evilplot.demo.DemoPlots.plotAreaSize
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._
import com.cibo.evilplot.numeric.{Point, Point3d}
import com.cibo.evilplot.plot.renderers.{PointRenderer, SurfaceRenderer}
import scala.util.Random

object PlottingHelpers {

  def makeMoons: (Seq[(Double, Double)], Seq[(Double, Double)]) = {
    def noise = Random.nextDouble() / 2d
    val points1 = (-Math.PI to Math.PI by 0.01d).map { i =>
      (-Math.PI / 4 + Math.cos(i) + noise, Math.abs(Math.sin(i)) + noise)
    }
    val points2 = (-Math.PI to Math.PI by 0.01d).map { i =>
      (-Math.PI / 4 + 1 - Math.cos(i) + noise, -1 * Math.abs(Math.sin(i)) + noise)
    }
    (points1, points2)
  }

  def scatterPlot(points: Seq[Point3d[Double]]) = {
    val plot = ScatterPlot(
      points,
      pointRenderer = Some(PointRenderer.depthColor[Point3d[Double]](
        x => x.z,
        points.map(_.z).min,
        points.map(_.z).max,
        Some(ContinuousColoring
          .gradient3(HTMLNamedColors.green, HTMLNamedColors.yellow, HTMLNamedColors.red)),
        None
      ))
    ).standard()
      .xLabel("x")
      .yLabel("y")
      .rightLegend()
      .render(plotAreaSize)
    displayPlot(plot)
  }
}
