package typeclass

import typeclass.data.Id

import scalaprops.Scalaprops

object FunctorTest extends Scalaprops {

  val id = FunctorLaws[Id].all

}
