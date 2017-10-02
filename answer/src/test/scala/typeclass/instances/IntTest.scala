package typeclass.instances

import typeclass.MonoidLaws
import typeclass.instances.int._

import scalaprops.Scalaprops

object IntTest extends Scalaprops {

  val monoid = MonoidLaws[Int].all

}
