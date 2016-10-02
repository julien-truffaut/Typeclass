package typeclass.std

import typeclass.MonoidLaws
import typeclass.std.string._

import scalaprops.{Gen, Scalaprops}

object StringTest extends Scalaprops {

  implicit val genString: Gen[String] = Gen.asciiString

  val monoid = MonoidLaws[String].all

}
