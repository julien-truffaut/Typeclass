package typeclass.data

import typeclass.ApplicativeLaws
import typeclass.Prelude._
import typeclass.std.list._

import scalaprops.Scalaprops

object ConstTest extends Scalaprops {

  val applicative = ApplicativeLaws[Const[List[Int], ?]].all

}
