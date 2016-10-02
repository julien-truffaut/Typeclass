package typeclass.data

import typeclass.Monoid

import scalaprops.Gen

case class First[A](value: Option[A])

object First {
  implicit def gen[A: Gen]: Gen[First[A]] = Gen[Option[A]].map(First(_))

  implicit def monoid[A]: Monoid[First[A]] = new Monoid[First[A]] {
    def empty: First[A] = First(None)
    def combine(x: First[A], y: First[A]): First[A] =
      x.value.fold(y)(_ => x)
  }
}