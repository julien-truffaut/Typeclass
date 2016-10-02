package typeclass.syntax

import typeclass.Semigroup

object semigroup {
  implicit class SemigroupOps[A](a: A)(implicit A: Semigroup[A]){
    def combine(other: A): A = ???
  }
}


