package typeclass.syntax

import typeclass.Functor
import typeclass.Prelude._

object functor {
  implicit class FunctorOps[F[_], A](fa: F[A])(implicit F: Functor[F]){
    def map[B](f: A => B): F[B] = F.map(fa)(f)
    def void: F[Unit] = F.void(fa)
    def as[B](b: B): F[B] = F.as(fa, b)
  }
}