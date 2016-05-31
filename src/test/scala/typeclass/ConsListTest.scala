package typeclass

import typeclass.Prelude._
import typeclass.data.List

import scalaprops.Scalaprops

object ConsListTest extends Scalaprops {

  val monad = MonadLaws[List].all

  val monoid = MonoidLaws[List[Int]].all

}
