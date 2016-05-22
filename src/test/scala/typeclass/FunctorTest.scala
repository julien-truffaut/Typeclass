package typeclass

import typeclass.data.{ConsList, Id}

import scalaprops.Scalaprops

object FunctorTest extends Scalaprops {

  val id = FunctorLaws[Id].all
  val consList = FunctorLaws[ConsList].all

}
