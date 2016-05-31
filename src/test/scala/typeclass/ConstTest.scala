package typeclass

import typeclass.data.{ConsList, Const}

import scalaprops.Scalaprops

object ConstTest extends Scalaprops {

  val applicative = ApplicativeLaws[Const[ConsList[Int], ?]].all

}
