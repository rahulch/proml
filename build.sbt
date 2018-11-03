lazy val commonSettings = Seq(
  name := "proml",
  version := "0.1",
  scalaVersion := "2.12.7",
  scalacOptions += "-Ypartial-unification"
)

lazy val core = (project in file("core")).settings(
  commonSettings
)

def module(moduleName: String, path: String, dependencies: Seq[ModuleID], dependencyResolvers: Seq[Resolver]) =
  Project(moduleName, file(path))
    .settings(commonSettings: _*)
    .settings(
      Defaults.itSettings,
      name := moduleName,
      libraryDependencies ++= dependencies,
      resolvers ++= dependencyResolvers
    ).dependsOn(core % "compile->compile;test->test")

lazy val examples = module(
  "Examples",
  "examples", Seq(
    "com.cibo" %% "evilplot" % "0.6.0",
    "com.cibo" %% "evilplot-repl" % "0.6.0",
    "org.plotly-scala" %% "plotly-core" % "0.4.2",
    "org.plotly-scala" %% "plotly-render" % "0.4.2"
  ), Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.bintrayRepo("cibotech", "public"),
    "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
    "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
    "Jzy3d releases" at "http://maven.jzy3d.org/releases/"
  )
)

lazy val tensorflowBackend = module(
  "Tensorflow",
  "backends/Tensorflow",
  Seq(
    "org.platanios" %% "tensorflow" % "0.2.4" classifier "darwin-cpu-x86_64",
    "org.platanios" %% "tensorflow-data" % "0.2.4"
  ), Nil
)

lazy val breezeBackend = module(
  "Breeze",
  "backends/Breeze",
  Seq(
    "org.scalanlp" %% "breeze" % "1.0-RC2",
    "org.scalanlp" %% "breeze-natives" % "1.0-RC2",
  ), Nil
)
