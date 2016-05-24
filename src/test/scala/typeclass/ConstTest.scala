package typeclass

import typeclass.data.Const

import scalaprops.Scalaprops

object ConstTest extends Scalaprops {

  val functor = FunctorLaws[Const[Int, ?]].all

}
