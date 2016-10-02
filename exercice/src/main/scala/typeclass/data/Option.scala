package typeclass.data

import typeclass.Semigroup

import scalaprops.Gen

sealed trait Option[A] {
  import Option._

  def fold[B](default: => B, f: A => B): B = this match {
    case None()  => default
    case Some(a) => f(a)
  }

  def getOrElse(a: => A): A =
    fold(a, identity)

  def orElse(opt: => Option[A]): Option[A] =
    fold(opt, some)
}

object Option {
  private case class None[A]() extends Option[A]
  private case class Some[A](value: A) extends Option[A]

  def none[A]: Option[A] = None()
  def some[A](a: A): Option[A] = Some(a)

  implicit def gen[A: Gen]: Gen[Option[A]] =
    Gen.frequency(
      (1, Gen.value(none)),
      (4, Gen[A].map(some))
    )

  implicit def optionSemigroup[A]: Semigroup[Option[A]] = ???
}
