package typeclass

import typeclass.data.{ConsList, Id}

import scalaprops.Scalaprops

object ConsListTest extends Scalaprops {

  val id = MonadLaws[ConsList].all

}
