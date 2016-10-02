package typeclass.data

import typeclass.{Applicative, Semigroup}

import scalaprops.Gen

sealed trait Validation[E, A] {
  import Validation._

  def fold[B](f: E => B, g: A => B): B = this match {
    case Failure(e) => f(e)
    case Success(a) => g(a)
  }
}

object Validation {
  private case class Failure[E, A](value: E) extends Validation[E, A]
  private case class Success[E, A](value: A) extends Validation[E, A]

  def failure[E, A](e: E): Validation[E, A] = Failure(e)
  def success[E, A](a: A): Validation[E, A] = Success(a)

  implicit def applicative[E](implicit E: Semigroup[E]): Applicative[Validation[E, ?]] = new Applicative[Validation[E, ?]] {
    def pure[A](a: A): Validation[E, A] = success(a)
    def ap[A, B](fab: Validation[E, A => B], fa: Validation[E, A]): Validation[E, B] =
      (fab, fa) match {
        case (Success(f), Success(a)) => Success(f(a))
        case (Failure(e), Success(_)) => Failure(e)
        case (Success(_), Failure(e)) => Failure(e)
        case (Failure(x), Failure(y)) => Failure(E.combine(x, y))
      }
  }

  implicit def gen[E: Gen, A: Gen]: Gen[Validation[E, A]] =
    Gen.oneOf(Gen[E].map(failure), Gen[A].map(success))
}