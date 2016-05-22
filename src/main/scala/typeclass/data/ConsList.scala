package typeclass.data

import typeclass.Functor

import scalaprops.Gen

sealed trait ConsList[A]

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


  implicit def gen[A: Gen]: Gen[ConsList[A]] =
    Gen.list[A].map(_.foldRight(nil[A])(cons))
}
