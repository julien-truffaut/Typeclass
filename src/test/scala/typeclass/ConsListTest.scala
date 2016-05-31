package typeclass

import typeclass.data.{ConsList, Id}

import scalaprops.Scalaprops

object ConsListTest extends Scalaprops {

  val monad = MonadLaws[ConsList].all

  val monoid = MonoidLaws[ConsList[Int]].all

}
