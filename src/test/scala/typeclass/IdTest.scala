package typeclass

import typeclass.data.Id

import scalaprops.Scalaprops

object IdTest extends Scalaprops {

  val monad = MonadLaws[Id].all

}
