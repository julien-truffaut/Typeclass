package typeclass.syntax

import typeclass.Applicative

object applicative {
  implicit class ApplicativeOps[F[_], A](fa: F[A])(implicit ev: Applicative[F]){
    def *>[B](fb: F[B]): F[B] = ???
    def <*[B](fb: F[B]): F[A] = ???

    def map2[B, C](fb: F[B])(f: (A, B) => C): F[C] = ???
    def map3[B, C, D](fb: F[B], fc: F[C])(f: (A, B, C) => D): F[D] = ???

    def tuple2[B](fb: F[B]): F[(A, B)] = ???
    def tuple3[B, C](fb: F[B], fc: F[C]): F[(A, B, C)] = ???
  }

  implicit class Applicative2Ops[F[_], A, B](fab: F[A => B])(implicit ev: Applicative[F]){
    def ap(fa: F[A]): F[B] = ???
  }

  implicit class Applicative3Ops[A](a: A){
    def pure[F[_]](implicit F: Applicative[F]) = ???
  }
}




