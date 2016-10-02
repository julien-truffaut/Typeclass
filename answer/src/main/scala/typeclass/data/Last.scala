package typeclass.data

import typeclass.Monoid
import typeclass.data.Option.none

import scalaprops.Gen

case class Last[A](value: Option[A])

object Last {
  implicit def gen[A: Gen]: Gen[Last[A]] = Gen[Option[A]].map(Last(_))

  implicit def monoid[A]: Monoid[Last[A]] = new Monoid[Last[A]] {
    def empty: Last[A] = Last(none[A])
    def combine(x: Last[A], y: Last[A]): Last[A] =
      y.value.fold(x, _ => y)
  }
}
