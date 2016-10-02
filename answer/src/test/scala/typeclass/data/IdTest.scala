package typeclass.data

import typeclass.MonadLaws

import scalaprops.Scalaprops

object IdTest extends Scalaprops {

  val monad = MonadLaws[Id].all

}
