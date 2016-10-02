package typeclass.data

import typeclass.Semigroup

import scalaprops.Gen

case class Mult(value: Int)

object Mult {
  implicit val gen: Gen[Mult] = Gen[Int].map(Mult(_))

  implicit val semigroup: Semigroup[Mult] = new Semigroup[Mult] {
    def combine(x: Mult, y: Mult): Mult = ???
  }
}