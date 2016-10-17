package typeclass.data

import typeclass.Semigroup

import scalaprops.Gen

case class First[A](value: Option[A])

object First {
  implicit def gen[A: Gen]: Gen[First[A]] = Gen[Option[A]].map(First(_))

  implicit def semigroup[A]: Semigroup[First[A]] = ???
}