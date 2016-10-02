package typeclass.data

import typeclass.Monad

import scalaprops.Gen

sealed trait Either[E, A] {
  import Either._

  def fold[B](f: E => B, g: A => B): B = this match {
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
      fa.fold(left, f)
  }

  implicit def gen[E: Gen, A: Gen]: Gen[Either[E, A]] =
    Gen.oneOf(Gen[E].map(left), Gen[A].map(right))
}