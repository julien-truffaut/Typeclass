package typeclass.instances

import typeclass.Monoid

object long {

  implicit val longMonoid: Monoid[Long] = new Monoid[Long] {
    def empty: Long = 0
    def combine(x: Long, y: Long): Long = x + y
  }

}