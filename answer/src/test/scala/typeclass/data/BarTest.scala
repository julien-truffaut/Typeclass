package typeclass.data

import typeclass.FunctorLaws

import scalaprops.Scalaprops

object BarTest extends Scalaprops {

  val functor = FunctorLaws[Bar].all

}
