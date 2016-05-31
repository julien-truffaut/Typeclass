package typeclass.syntax

import typeclass.Applicative

object applicative {
  /** pimp F[A] with all methods of ApplicativeOps if F has an instance of Applicative */
  implicit def applicativeOps[F[_], A](fa: F[A])(implicit F: Applicative[F]): ApplicativeOps[F, A] = new ApplicativeOps(fa)
  implicit def applicativeOps2[A](a: A): Applicative2Ops[A] = new Applicative2Ops(a)
}

class ApplicativeOps[F[_], A](fa: F[A])(implicit F: Applicative[F]){
  def ap[B](fab: F[A => B]): F[B] =
    F.ap(fab, fa)

  def *>[B](fb: F[B]): F[B] = F.*>(fa, fb)
  def <*[B](fb: F[B]): F[A] = F.<*(fa, fb)

  def map2[B, C](fb: F[B])(f: (A, B) => C): F[C] = F.map2(fa, fb)(f)
  def map3[B, C, D](fb: F[B], fc: F[C])(f: (A, B, C) => D): F[D] = F.map3(fa, fb, fc)(f)

  def tuple2[B](fb: F[B]): F[(A, B)] = F.tuple2(fa, fb)
  def tuple3[B, C](fb: F[B], fc: F[C]): F[(A, B, C)] = F.tuple3(fa, fb, fc)
}

class Applicative2Ops[A](a: A){
  def pure[F[_]](implicit F: Applicative[F]) = F.pure(a)
}
