package typeclass.data

import typeclass.SemigroupLaws

import scalaprops.Scalaprops

object MultTest extends Scalaprops {

  val monoid = SemigroupLaws[Mult].all

}
