package typeclass.instances

import typeclass.{MonadLaws, MonoidLaws}
import typeclass.instances.int._
import typeclass.instances.list._

import scalaprops.{Param, Scalaprops}

object ListTest extends Scalaprops {

  val monoid = MonoidLaws[List[Int]].all
  val monad = MonadLaws[List].all.andThenParam(Param.maxSize(10))

}
