package typeclass.std

import typeclass.{MonadLaws, MonoidLaws}
import typeclass.std.int._
import typeclass.std.list._

import scalaprops.{Param, Scalaprops}

object ListTest extends Scalaprops {

  val monoid = MonoidLaws[List[Int]].all
  val monad = MonadLaws[List].all.andThenParam(Param.maxSize(10))

}
