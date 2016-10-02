package typeclass.data

import typeclass.SemigroupLaws
import typeclass.std.option._

import scalaprops.{Gen, Scalaprops}

object OptionTest extends Scalaprops {
  implicit val stringGen: Gen[String] = Gen.asciiString

  val semigroup = SemigroupLaws[Option[String]].all
}
