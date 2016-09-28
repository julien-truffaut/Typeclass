package typeclass

import scalaprops.{Gen, Scalaprops}

object SemigroupTest extends Scalaprops {
  implicit val genString: Gen[String] = Gen.asciiString

  val string = SemigroupLaws[String].all
  val int    = SemigroupLaws[Int].all
  val list   = SemigroupLaws[List[Int]].all

}
