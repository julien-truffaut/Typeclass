package typeclass.data

import typeclass.Functor

import scalaprops.Gen

case class Const[A, B](value: A)

object Const {
  // need R: Monoid to create an applicative
  implicit def functor[R]: Functor[Const[R, ?]] = new Functor[Const[R, ?]] {
    def map[A, B](fa: Const[R, A])(f: A => B): Const[R, B] = Const(fa.value)
  }

  implicit def gen[A: Gen, B]: Gen[Const[A, B]] = Gen[A].map(Const(_))
}
