package typeclass.data

import typeclass.{Foldable, Monad}

import scalaprops.Gen

sealed trait Either[E, A] {
  import Either._

  def cata[B](f: E => B, g: A => B): B = this match {
    case Left(e)  => f(e)
    case Right(a) => g(a)
  }
}

object Either {
  private case class Left[E, A](value: E) extends Either[E, A]
  private case class Right[E, A](value: A) extends Either[E, A]

  def left[E, A](e: E): Either[E, A] = Left(e)
  def right[E, A](a: A): Either[E, A] = Right(a)

  implicit def monad[E]: Monad[Either[E, ?]] = new Monad[Either[E, ?]] {
    def pure[A](a: A): Either[E, A] = right(a)
    def flatMap[A, B](fa: Either[E, A])(f: A => Either[E, B]): Either[E, B] =
      fa.cata(left, f)
  }

  implicit def foldable[E]: Foldable[Either[E, ?]] = new Foldable[Either[E, ?]] {
    def foldLeft[A, B](fa: Either[E, A], z: B)(f: (B, A) => B): B =
      fa.cata(_ => z, f(z, _))

    def foldRight[A, B](fa: Either[E, A], z: B)(f: (A, B) => B): B =
      fa.cata(_ => z, f(_, z))
  }

  implicit def gen[E: Gen, A: Gen]: Gen[Either[E, A]] =
    Gen.oneOf(Gen[E].map(left), Gen[A].map(right))
}