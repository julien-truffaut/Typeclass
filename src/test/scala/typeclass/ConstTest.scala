package typeclass

import typeclass.data.{ConsList, Const}

import scalaprops.Scalaprops

object ConstTest extends Scalaprops {

  val applicative = FunctorLaws[Const[ConsList[Int], ?]].all

}
