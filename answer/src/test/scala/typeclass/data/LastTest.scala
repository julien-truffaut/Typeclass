package typeclass.data

import typeclass.MonoidLaws

import scalaprops.Scalaprops

object LastTest extends Scalaprops {

  val monoid = MonoidLaws[Last[Int]].all

}
