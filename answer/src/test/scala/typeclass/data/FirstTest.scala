package typeclass.data

import typeclass.MonoidLaws

import scalaprops.Scalaprops

object FirstTest extends Scalaprops {

  val monoid = MonoidLaws[First[Int]].all

}
