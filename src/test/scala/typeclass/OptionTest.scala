package typeclass

import typeclass.data.Option

import scalaprops.Scalaprops

object OptionTest extends Scalaprops {

  val monad = MonadLaws[Option].all
  val foldable = FoldableLaws[Option].all
}
