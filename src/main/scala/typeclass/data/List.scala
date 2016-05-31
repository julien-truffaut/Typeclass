package typeclass.data

import typeclass.{Monad, Monoid, Semigroup}

import scala.annotation.tailrec
import scalaprops.Gen

sealed trait List[A] {
  import List._

  @tailrec
  final def foldLeft[B](z: B)(f: (B, A) => B): B = this match {
    case Nil()      => z
    case Cons(h, t) => t.foldLeft(f(z, h))(f)
  }

  final def foldRight[B](z: B)(f: (A, B) => B): B =
    reverse.foldLeft(z)((b, a) => f(a , b))

  final def reverse: List[A] =
    foldLeft(nil[A])((acc, a) => cons(a, acc))

  /** alias for combine */
  final def ++(other: List[A]): List[A] =
    Semigroup[List[A]].combine(this, other)
}

object List {
  private case class Nil[A]() extends List[A]
  private case class Cons[A](head: A, tail: List[A]) extends List[A]

  def nil[A]: List[A] = Nil()
  def cons[A](head: A, tail: List[A]): List[A] = Cons(head, tail)

  implicit val monad: Monad[List] = new Monad[List] {
    def pure[A](a: A): List[A] = Cons(a, nil)

   def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] =
     fa.foldRight(nil[B])((a, acc) => f(a) ++ acc)
  }

  implicit def monoid[A]: Monoid[List[A]] = new Monoid[List[A]] {
    def combine(x: List[A], y: List[A]): List[A] =
      x.foldRight(y)(cons)
    def empty: List[A] = nil
  }

  implicit def gen[A: Gen]: Gen[List[A]] =
    Gen.list[A].map(_.foldRight(nil[A])(cons))
}
