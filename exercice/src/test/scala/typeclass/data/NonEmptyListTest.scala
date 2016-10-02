package typeclass.data

import typeclass.SemigroupLaws

import scalaprops.Scalaprops

object NonEmptyListTest extends Scalaprops {

  val semigroup = SemigroupLaws[NonEmptyList[Int]].all

}