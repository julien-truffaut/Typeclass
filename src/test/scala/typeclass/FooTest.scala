package typeclass

import typeclass.data.Foo

import scalaprops.Scalaprops

object FooTest extends Scalaprops {

  val functor = FunctorLaws[Foo].all

}
