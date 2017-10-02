package typeclass.instances

import typeclass.Semigroup

object int {

  implicit val intSemigroup: Semigroup[Int] = new Semigroup[Int] {
    def combine(x: Int, y: Int): Int = ???
  }

}