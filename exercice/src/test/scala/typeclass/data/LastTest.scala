package typeclass.data

import typeclass.SemigroupLaws

import scalaprops.Scalaprops

object LastTest extends Scalaprops {

  val monoid = SemigroupLaws[Last[Int]].all

}
