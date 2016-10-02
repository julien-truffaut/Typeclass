package typeclass.data

import typeclass.Prelude._
import typeclass.{Foldable, Monad, Monoid, Semigroup}

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

  def toEither[E](e: => E): Either[E, A] =
    fold(Either.left(e), Either.right)
}

object Option {
  private case class None[A]() extends Option[A]
  private case class Some[A](value: A) extends Option[A]

  def none[A]: Option[A] = None()
  def some[A](a: A): Option[A] = Some(a)

  implicit val monad: Monad[Option] = new Monad[Option] {
    def pure[A](a: A): Option[A] = some(a)
    def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
      fa.fold(none, f)
  }

  implicit val foldable: Foldable[Option] = new Foldable[Option] {
    def foldLeft[A, B](fa: Option[A], z: B)(f: (B, A) => B): B =
      fa.fold(z, f(z, _))

    def foldRight[A, B](fa: Option[A], z: B)(f: (A, B) => B): B =
      fa.fold(z, f(_, z))
  }

  implicit def optionMonoid[A: Semigroup]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def empty: Option[A] = none
    def combine(x: Option[A], y: Option[A]): Option[A] = (x, y) match {
      case (None() , None())  => none
      case (Some(a), None())  => some(a)
      case (None() , Some(a)) => some(a)
      case (Some(a), Some(b)) => some(Semigroup[A].combine(a, b))
    }
  }


  implicit def gen[A: Gen]: Gen[Option[A]] =
    Gen.frequency(
      (1, Gen.value(none)),
      (4, Gen[A].map(some))
    )
}
