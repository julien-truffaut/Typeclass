package typeclass.data

import typeclass.SemigroupLaws

import scalaprops.Scalaprops

object FirstTest extends Scalaprops {

  val monoid = SemigroupLaws[First[Int]].all

}
