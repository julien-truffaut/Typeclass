package typeclass

import typeclass.data.Bar

import scalaprops.Scalaprops

object BarTest extends Scalaprops {

  val functor = FunctorLaws[Bar].all

}
