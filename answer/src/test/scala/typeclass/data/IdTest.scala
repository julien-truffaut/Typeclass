package typeclass.data

import typeclass.{FoldableLaws, MonadLaws}

import scalaprops.Scalaprops

object IdTest extends Scalaprops {

  val monad = MonadLaws[Id].all
  val foldable = FoldableLaws[Id].all

}
