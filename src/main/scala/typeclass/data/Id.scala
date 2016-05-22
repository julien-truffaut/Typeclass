package typeclass.data

import typeclass.Functor

import scalaprops.Gen

case class Id[A](value: A)

object Id {
  implicit def gen[A](implicit genA: Gen[A]): Gen[Id[A]] =
    genA.map(Id(_))

  implicit val functor: Functor[Id] = new Functor[Id] {
    def map[A, B](fa: Id[A])(f: A => B): Id[B] = Id(f(fa.value))
  }
}
