lazy val baseSettings: Seq[Setting[_]] = Seq(
  organization       := "com.github.julien-truffaut",
  scalaOrganization  := "org.typelevel",
  scalaVersion       := "2.11.8",
  scalacOptions     ++= Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions", "-language:existentials",
    "-unchecked",
    "-Xfatal-warnings",
    "-Xlint",
    "-Yno-adapted-args",
    //  "-Yno-predef", "-Yno-imports",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture"
  ),
  resolvers += Resolver.sonatypeRepo("releases"),
  libraryDependencies ++= Seq(
    "com.github.mpilquist"  %% "simulacrum" % "0.8.0",
    "com.github.scalaprops" %% "scalaprops" % "0.3.4"
  ),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.0")
)

lazy val typeclass = project.in(file("."))
  .settings(moduleName := "typeclass")
  .settings(baseSettings)
  .aggregate(answer, exercice)
  .dependsOn(answer, exercice)

lazy val answer = project
  .settings(moduleName := "typeclass-answer")
  .settings(baseSettings)
  .settings(testSettings)

lazy val exercice = project
  .settings(moduleName := "typeclass-exercice")
  .settings(baseSettings)
  .settings(testSettings)

lazy val slides = project
  .settings(moduleName := "typeclass-slides")
  .settings(baseSettings)
  .settings(tutSettings)
  .settings(
    tutSourceDirectory := baseDirectory.value / "tut",
    tutTargetDirectory := baseDirectory.value / "tut-out"
  ).dependsOn(answer)

lazy val testSettings = Seq(
  testFrameworks += new TestFramework("scalaprops.ScalapropsFramework"),
  parallelExecution in Test := false
)