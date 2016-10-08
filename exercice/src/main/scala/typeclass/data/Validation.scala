package typeclass.data

import typeclass.Functor

import scalaprops.Gen

sealed trait Validation[E, A] {
  import Validation._

  def fold[B](f: E => B, g: A => B): B = this match {
    case Failure(e) => f(e)
    case Success(a) => g(a)
  }
}

object Validation {
  case class Failure[E, A](value: E) extends Validation[E, A]
  case class Success[E, A](value: A) extends Validation[E, A]

  def failure[E, A](e: E): Validation[E, A] = Failure(e)
  def success[E, A](a: A): Validation[E, A] = Success(a)

  implicit def validationFunctor[E]: Functor[Validation[E, ?]] = new Functor[Validation[E, ?]] {
    def map[A, B](fa: Validation[E, A])(f: (A) => B): Validation[E, B] = ???
  }

  implicit def gen[E: Gen, A: Gen]: Gen[Validation[E, A]] =
    Gen.oneOf(Gen[E].map(failure), Gen[A].map(success))
}