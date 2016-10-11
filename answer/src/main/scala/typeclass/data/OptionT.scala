package typeclass.data

import typeclass.{Functor, Monad}

import scalaprops.Gen

case class OptionT[F[_], A](value: F[Option[A]])

object OptionT {
  implicit def gen[F[_], A](implicit gen: Gen[F[Option[A]]]): Gen[OptionT[F, A]] =
    gen.map(OptionT(_))

  implicit def optionTFunctor[F[_]](implicit F: Functor[F]): Functor[OptionT[F, ?]] = new Functor[OptionT[F, ?]] {
    def map[A, B](fa: OptionT[F, A])(f: A => B): OptionT[F, B] =
      OptionT(Functor[F].map(fa.value)(_.map(f)))
  }

  implicit def optionTMonad[F[_]](implicit F: Monad[F]): Monad[OptionT[F, ?]] = new Monad[OptionT[F, ?]] {
    def flatMap[A, B](fa: OptionT[F, A])(f: A => OptionT[F, B]): OptionT[F, B] =
      OptionT(F.flatMap(fa.value)(_.fold(F.pure[Option[B]](None))(a => f(a).value)))

    def pure[A](a: A): OptionT[F, A] = OptionT(Monad[F].map(Monad[F].pure(a))(Option.apply))
  }
}
