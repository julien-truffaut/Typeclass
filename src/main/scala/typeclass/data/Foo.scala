package typeclass.data

import typeclass.Functor
import typeclass.Prelude._

import scalaprops.Gen


case class Foo[A](b: Boolean, i: Int, a: A)

object Foo {
  implicit val functor: Functor[Foo] = new Functor[Foo] {
    def map[A, B](fa: Foo[A])(f: A => B): Foo[B] =
      fa.copy(a = f(fa.a))
  }

  implicit def gen[A: Gen]: Gen[Foo[A]] =
    for {
      b <- Gen.genBoolean
      i <- Gen.genIntAll
      a <- Gen[A]
    } yield Foo(b, i, a)
}
