package typeclass

import typeclass.data.Id

import scalaprops.Scalaprops

object IdTest extends Scalaprops {

  val id = MonadLaws[Id].all

}
