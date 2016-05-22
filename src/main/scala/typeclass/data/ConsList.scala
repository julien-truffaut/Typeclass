package typeclass.data

import typeclass.Monad

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

  implicit val monad: Monad[ConsList] = new Monad[ConsList] {
    def pure[A](a: A): ConsList[A] = Cons(a, nil)

   def flatMap[A, B](fa: ConsList[A])(f: A => ConsList[B]): ConsList[B] =
     fa.foldRight(nil[B])((a, acc) => f(a) ++ acc)
  }

  implicit def gen[A: Gen]: Gen[ConsList[A]] =
    Gen.list[A].map(_.foldRight(nil[A])(cons))
}
