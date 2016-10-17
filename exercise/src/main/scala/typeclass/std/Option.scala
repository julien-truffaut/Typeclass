package typeclass.std

import typeclass.Semigroup

object option {

  implicit def optionSemigroup[A]: Semigroup[Option[A]] = new Semigroup[Option[A]] {
    def combine(x: Option[A], y: Option[A]): Option[A] = ???
  }

}
