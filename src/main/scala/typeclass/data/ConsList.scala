package typeclass.data

import typeclass.{Applicative, Functor}

import scalaprops.Gen

sealed trait ConsList[A] {
  import ConsList._

//  @tailrec
//  final def foldLeft[B](z: B)(f: (B, A) => B): B = this match {
//    case Nil()      => z
//    case Cons(h, t) => foldLeft(f(z, h))(f)
//  }

  final def foldLeft[B](b: B)(f: (B, A) => B): B = {
    var acc = b
    var l = this
    while(true){
      l match {
        case Cons(h, t) =>
          acc = f(acc, h)
          l = t
        case Nil() => return acc
      }
    }
    acc
  }

  final def foldRight[B](z: B)(f: (A, B) => B): B =
    reverse.foldLeft(z)((b, a) => f(a , b))

  final def reverse: ConsList[A] =
    foldLeft(nil[A])((acc, a) => cons(a, acc))

  final def ++(other: ConsList[A]): ConsList[A] =
    foldRight(other)(cons)
}

object ConsList {
  private case class Nil[A]() extends ConsList[A]
  private case class Cons[A](head: A, tail: ConsList[A]) extends ConsList[A]

  def nil[A]: ConsList[A] = Nil()
  def cons[A](head: A, tail: ConsList[A]): ConsList[A] = Cons(head, tail)

  implicit val functor: Functor[ConsList] = new Functor[ConsList] {
    def map[A, B](fa: ConsList[A])(f: A => B): ConsList[B] =
      fa match {
        case Nil()      => Nil()
        case Cons(h, t) => Cons(f(h), map(t)(f))
      }
  }

  implicit val applicative: Applicative[ConsList] = new Applicative[ConsList] {
    def functor: Functor[ConsList] = Functor[ConsList]

    def pure[A](a: A): ConsList[A] = Cons(a, nil)

    def ap[A, B](fab: ConsList[A => B], fa: ConsList[A]): ConsList[B] =
      fab.foldRight(nil[B])((f, acc) =>
        fa.foldRight(nil[B])((a, acc2) => cons(f(a), acc2)) ++ acc
      )
  }

  implicit def gen[A: Gen]: Gen[ConsList[A]] =
    Gen.list[A].map(_.foldRight(nil[A])(cons))
}
