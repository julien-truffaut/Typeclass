name := "Typeclass"
version := "0.1"
scalaVersion := "2.11.8"
scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:existentials",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Yno-predef",
  "-Yno-imports",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture"
)

testFrameworks += new TestFramework("scalaprops.ScalapropsFramework")
parallelExecution in Test := false

libraryDependencies ++= Seq(
  "com.github.scalaprops" %% "scalaprops" % "0.3.4"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.0")