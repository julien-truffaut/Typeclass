package typeclass.data

import typeclass.{Applicative, Functor}

import scalaprops.Gen

case class Id[A](value: A)

object Id {
  implicit def gen[A](implicit genA: Gen[A]): Gen[Id[A]] =
    genA.map(Id(_))

  implicit val functor: Functor[Id] = new Functor[Id] {
    def map[A, B](fa: Id[A])(f: A => B): Id[B] = Id(f(fa.value))
  }

  implicit val applicative: Applicative[Id] = new Applicative[Id] {
    def functor: Functor[Id] = Functor[Id]

    def pure[A](a: A): Id[A] = Id(a)

    def ap[A, B](fab: Id[A => B], fa: Id[A]): Id[B] =
      Id(fab.value(fa.value))
  }
}
