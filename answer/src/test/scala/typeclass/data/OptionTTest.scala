package typeclass.data

import typeclass.std.list._
import typeclass.{FunctorLaws, MonadLaws}

import scalaprops.Scalaprops

object OptionTTest extends Scalaprops {

  val functor = FunctorLaws[OptionT[List, ?]].all
  val monad = MonadLaws[OptionT[List, ?]].all

}
