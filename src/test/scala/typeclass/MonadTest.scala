package typeclass

import typeclass.data.{ConsList, Id}

import scalaprops.Scalaprops

object MonadTest extends Scalaprops {

  val id = MonadLaws[Id].all
  val consList = MonadLaws[ConsList].all

  // check syntax compile
  import typeclass.syntax.monad._

  Id(3).flatMap(i => Id(i + 1)) // Id(4)
  //Id(Id(0)).flatten // Id(0)

}
