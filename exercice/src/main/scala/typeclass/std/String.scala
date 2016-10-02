package typeclass.std

import typeclass.Semigroup

object string {

  implicit val stringSemigroup: Semigroup[String] = new Semigroup[String] {
    def combine(x: String, y: String): String = ???
  }

}