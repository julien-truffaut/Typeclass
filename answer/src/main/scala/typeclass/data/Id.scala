package typeclass.data

import typeclass.Monad

import scalaprops.Gen

case class Id[A](value: A)

object Id {
  implicit def gen[A](implicit genA: Gen[A]): Gen[Id[A]] =
    genA.map(Id(_))

  implicit val monad: Monad[Id] = new Monad[Id] {
    def pure[A](a: A): Id[A] = Id(a)
    def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa.value)
  }

}
