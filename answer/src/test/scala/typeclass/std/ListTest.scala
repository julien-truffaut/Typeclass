package typeclass.std

import typeclass.MonoidLaws
import typeclass.std.int._
import typeclass.std.list._

import scalaprops.Scalaprops

object ListTest extends Scalaprops {

  val monoid = MonoidLaws[List[Int]].all

}
