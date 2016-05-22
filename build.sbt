name := "Typeclass"
version := "0.1"
scalaVersion := "2.11.8"
scalacOptions ++= Seq("-feature")

testFrameworks += new TestFramework("scalaprops.ScalapropsFramework")
parallelExecution in Test := false

libraryDependencies ++= Seq(
  "com.github.scalaprops" %% "scalaprops" % "0.3.1"
)