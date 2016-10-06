package typeclass.syntax

import typeclass.Functor

object functor {
  implicit class FunctorOps[F[_], A](fa: F[A])(implicit F: Functor[F]){
    def map[B](f: A => B): F[B] = ???
    def void: F[Unit] = ???
    def as[B](b: B): F[B] = ???
  }
}