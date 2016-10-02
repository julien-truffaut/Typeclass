package typeclass.data

import typeclass.Monoid

import scalaprops.Gen

case class Mult(value: Int)

object Mult {
  implicit val gen: Gen[Mult] = Gen[Int].map(Mult(_))

  implicit val monoid: Monoid[Mult] = new Monoid[Mult] {
    def empty: Mult = Mult(1)
    def combine(x: Mult, y: Mult): Mult = Mult(x.value * y.value)
  }
}