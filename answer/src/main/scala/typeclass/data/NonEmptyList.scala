package typeclass.data

import typeclass.{Monad, Semigroup}

import scalaprops.Gen

final case class NonEmptyList[A](head: A, tail: List[A]){
  def toList: List[A] = head :: tail
}

object NonEmptyList {
  def apply[A](a: A, as: A*): NonEmptyList[A] =
    NonEmptyList(a, as.toList)

  implicit val nelMonad: Monad[NonEmptyList] = new Monad[NonEmptyList] {
    def pure[A](a: A): NonEmptyList[A] = NonEmptyList(a, Nil)
    def flatMap[A, B](fa: NonEmptyList[A])(f: A => NonEmptyList[B]): NonEmptyList[B] = {
      val first = f(fa.head)
      first.copy(tail = first.tail ++ fa.tail.flatMap(f(_).toList))
    }
  }

  implicit def nelSemigroup[A]: Semigroup[NonEmptyList[A]] = new Semigroup[NonEmptyList[A]] {
    def combine(x: NonEmptyList[A], y: NonEmptyList[A]): NonEmptyList[A] =
      x.copy(tail = x.tail ++ y.toList)
  }

  implicit def nelGen[A: Gen]: Gen[NonEmptyList[A]] = for {
    head <- Gen[A]
    tail <- Gen[List[A]]
  } yield NonEmptyList(head, tail)

}
