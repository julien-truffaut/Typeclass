package typeclass.data

import typeclass.SemigroupLaws

import scalaprops.{Gen, Scalaprops}

object OptionTest extends Scalaprops {
  implicit val stringGen: Gen[String] = Gen.asciiString

  val semigroup = SemigroupLaws[Option[String]].all
}
