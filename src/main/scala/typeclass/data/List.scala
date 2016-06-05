package typeclass.data

import typeclass.{Foldable, Monad, Monoid, Semigroup}
import typeclass.syntax.applicative._
import typeclass.syntax.foldable._

import scala.annotation.tailrec
import scalaprops.Gen

sealed trait List[A] {
  import List._

  final def :+(a: A): List[A] = reverse.foldLeft(a.pure[List])((acc, a) => a +: acc)
  final def +:(a: A): List[A] = cons(a, this)

  final def reverse: List[A] =
    this.foldLeft(nil[A])((acc, a) => cons(a, acc))

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
     fa.reverse.foldLeft(nil[B])((acc, a) => f(a) ++ acc)
  }

  implicit def monoid[A]: Monoid[List[A]] = new Monoid[List[A]] {
    def combine(x: List[A], y: List[A]): List[A] =
      x.reverse.foldLeft(y)((acc, a) => cons(a, acc))
    def empty: List[A] = nil
  }

  implicit val foldable: Foldable[List] = new Foldable[List] {
    @tailrec
    def foldLeft[A, B](fa: List[A], z: B)(f: (B, A) => B): B = fa match {
      case Nil()      => z
      case Cons(h, t) => foldLeft(t, f(z, h))(f)
    }

    def foldRight[A, B](fa: List[A], z: B)(f: (A, B) => B): B = fa match {
      case Nil()      => z
      case Cons(h, t) => f(h, foldRight(t, z)(f))
    }
  }

  implicit def gen[A: Gen]: Gen[List[A]] =
    Gen.list[A].map(_.foldRight(nil[A])(cons))
}
