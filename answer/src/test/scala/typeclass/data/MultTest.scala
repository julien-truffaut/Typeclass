package typeclass.data

import typeclass.MonoidLaws

import scalaprops.Scalaprops

object MultTest extends Scalaprops {

  val monoid = MonoidLaws[Mult].all

}
