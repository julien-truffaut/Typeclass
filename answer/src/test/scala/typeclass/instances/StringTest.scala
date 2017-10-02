package typeclass.instances

import typeclass.MonoidLaws
import typeclass.instances.string._

import scalaprops.{Gen, Scalaprops}

object StringTest extends Scalaprops {

  implicit val genString: Gen[String] = Gen.asciiString

  val monoid = MonoidLaws[String].all

}
