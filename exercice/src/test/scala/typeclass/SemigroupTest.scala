package typeclass

import typeclass.std.int._
import typeclass.std.list._
import typeclass.std.string._

import scalaprops.{Gen, Scalaprops}

object SemigroupTest extends Scalaprops {
  implicit val genString: Gen[String] = Gen.asciiString

  val string = SemigroupLaws[String].all
  val int    = SemigroupLaws[Int].all
  val list   = SemigroupLaws[List[Int]].all

}
