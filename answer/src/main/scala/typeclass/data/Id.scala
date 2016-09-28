package typeclass.data

import typeclass.{Foldable, Monad}

import scalaprops.Gen

case class Id[A](value: A)

object Id {
  implicit def gen[A](implicit genA: Gen[A]): Gen[Id[A]] =
    genA.map(Id(_))

  implicit val monad: Monad[Id] = new Monad[Id] {
    def pure[A](a: A): Id[A] = Id(a)
    def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa.value)
  }

  implicit val foldable: Foldable[Id] = new Foldable[Id] {
    def foldLeft[A, B](fa: Id[A], z: B)(f: (B, A) => B): B =
      f(z, fa.value)
    def foldRight[A, B](fa: Id[A], z: B)(f: (A, B) => B): B =
      f(fa.value, z)
  }
}
