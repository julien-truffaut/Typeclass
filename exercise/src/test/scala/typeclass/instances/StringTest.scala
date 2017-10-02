package typeclass.instances

import typeclass.SemigroupLaws

import scalaprops.{Gen, Scalaprops}

object StringTest extends Scalaprops {

  implicit val genString: Gen[String] = Gen.asciiString

//  val semigroup = SemigroupLaws[String].all

}
