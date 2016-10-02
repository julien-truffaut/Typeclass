package typeclass.std

import typeclass.MonoidLaws
import typeclass.std.int._

import scalaprops.Scalaprops

object IntTest extends Scalaprops {

  val monoid = MonoidLaws[Int].all

}
