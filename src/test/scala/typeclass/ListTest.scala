package typeclass

import typeclass.Prelude._
import typeclass.data.List

import scalaprops.{Param, Scalaprops}

object ListTest extends Scalaprops {

  val monad = MonadLaws[List].all.andThenParam(Param.maxSize(10))
  val monoid = MonoidLaws[List[Int]].all
  val foldable = FoldableLaws[List].all

}
