package typeclass

import typeclass.data.{ConsList, Id}

import scalaprops.Scalaprops

object FunctorTest extends Scalaprops {

  val id = FunctorLaws[Id].all
  val consList = FunctorLaws[ConsList].all

  // check syntax compile
  import typeclass.syntax.functor._
  Id(3).map(_ + 1) // Id(4)

}
