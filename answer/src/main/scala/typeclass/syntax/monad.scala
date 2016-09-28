package typeclass.syntax

import typeclass.Monad
import typeclass.Prelude._

object monad {
  /** pimp F[A] with all methods of MonadOps if F has an instance of Monad */
  implicit def monadOps[F[_], A](fa: F[A])(implicit F: Monad[F]): MonadOps[F, A] = new MonadOps(fa)
}

class MonadOps[F[_], A](fa: F[A])(implicit F: Monad[F]){
  def flatMap[B](f: A => F[B]): F[B] = F.flatMap(fa)(f)
  def flatten[B](implicit ev: A <:< F[B]): F[B] = F.flatten(F.map(fa)(ev))
  def as[B](b: B): F[B] = F.as(fa, b)
}
