package typeclass.data

import typeclass.Semigroup

import scalaprops.Gen

final case class NonEmptyList[A](head: A, tail: List[A]){
  def toList: List[A] = head :: tail
}

object NonEmptyList {
  def apply[A](a: A, as: A*): NonEmptyList[A] =
    NonEmptyList(a, as.toList)

  implicit def nelSemigroup[A]: Semigroup[NonEmptyList[A]] = ???

  implicit def nelGen[A: Gen]: Gen[NonEmptyList[A]] = for {
    head <- Gen[A]
    tail <- Gen[List[A]]
  } yield NonEmptyList(head, tail)

}
