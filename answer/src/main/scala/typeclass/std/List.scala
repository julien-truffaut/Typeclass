package typeclass.std

import typeclass.Monoid

object list {

  implicit def listMonoid[A]: Monoid[List[A]] = new Monoid[List[A]] {
    def empty: List[A] = List()
    def combine(x: List[A], y: List[A]): List[A] = x ++ y
  }

}
