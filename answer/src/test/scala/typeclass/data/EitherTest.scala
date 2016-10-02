package typeclass.data

import typeclass.Prelude._
import typeclass.{FoldableLaws, MonadLaws}

import scalaprops.Scalaprops

object EitherTest extends Scalaprops {

  val monad = MonadLaws[Either[Boolean, ?]].all
  val foldable = FoldableLaws[Either[Boolean, ?]].all
}
