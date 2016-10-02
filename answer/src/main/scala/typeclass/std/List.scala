package typeclass.std

import typeclass.{Monad, Monoid}

object list {

  implicit def listMonoid[A]: Monoid[List[A]] = new Monoid[List[A]] {
    def empty: List[A] = List()
    def combine(x: List[A], y: List[A]): List[A] = x ++ y
  }

  implicit val listMonad: Monad[List] = new Monad[List] {
    def pure[A](a: A): List[A] = List(a)
    def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)
  }

}
