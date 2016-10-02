package typeclass.data

import typeclass.Semigroup

import scalaprops.Gen

case class Last[A](value: Option[A])

object Last {
  implicit def gen[A: Gen]: Gen[Last[A]] = Gen[Option[A]].map(Last(_))

  implicit def semigroup[A]: Semigroup[Last[A]] = ???
}
