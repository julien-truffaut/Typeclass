package typeclass.syntax

import typeclass.Semigroup

object semigroup {
  /** pimp A with all methods of SemigroupOps if A has an instance of Semigroup */
  implicit def semigroupOps[A](a: A)(implicit A: Semigroup[A]): SemigroupOps[A] = new SemigroupOps(a)
}

class SemigroupOps[A](a: A)(implicit A: Semigroup[A]){
  def combine(other: A): A = A.combine(a, other)
}
