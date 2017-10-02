package typeclass.instances

import typeclass.Semigroup

object list {

  implicit def listSemigroup[A]: Semigroup[List[A]] = new Semigroup[List[A]] {
    def combine(x: List[A], y: List[A]): List[A] = ???
  }

}
