package typeclass.data

import typeclass.Prelude._
import typeclass.{MonadLaws, SemigroupLaws}

import scalaprops.{Param, Scalaprops}

object NonEmptyListTest extends Scalaprops {

  val monad = MonadLaws[NonEmptyList].all.andThenParam(Param.maxSize(10))
  val semigroup = SemigroupLaws[NonEmptyList[Int]].all

}