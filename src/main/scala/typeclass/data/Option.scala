package typeclass.data

import typeclass.Prelude._
import typeclass.{Foldable, Monad}

import scalaprops.Gen

sealed trait Option[A] {
  import Option._

  def cata[B](default: => B, f: A => B): B = this match {
    case None()  => default
    case Some(a) => f(a)
  }

  def getOrElse(a: => A): A =
    cata(a, identity)

  def orElse(opt: => Option[A]): Option[A] =
    cata(opt, some)

  def toEither[E](e: => E): Either[E, A] =
    cata(Either.left(e), Either.right)
}

object Option {
  private case class None[A]() extends Option[A]
  private case class Some[A](value: A) extends Option[A]

  def none[A]: Option[A] = None()
  def some[A](a: A): Option[A] = Some(a)

  implicit val monad: Monad[Option] = new Monad[Option] {
    def pure[A](a: A): Option[A] = some(a)
    def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
      fa.cata(none, f)
  }

  implicit val foldable: Foldable[Option] = new Foldable[Option] {
    def foldLeft[A, B](fa: Option[A], z: B)(f: (B, A) => B): B =
      fa.cata(z, f(z, _))

    def foldRight[A, B](fa: Option[A], z: B)(f: (A, B) => B): B =
      fa.cata(z, f(_, z))
  }

  implicit def gen[A: Gen]: Gen[Option[A]] =
    Gen.frequency(
      (1, Gen.value(none)),
      (4, Gen[A].map(some))
    )
}
