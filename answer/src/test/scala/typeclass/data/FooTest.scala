package typeclass.data

import typeclass.FunctorLaws

import scalaprops.Scalaprops

object FooTest extends Scalaprops {

  val functor = FunctorLaws[Foo].all

}
