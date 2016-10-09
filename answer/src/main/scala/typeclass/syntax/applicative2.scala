package typeclass.syntax

import typeclass.Applicative

import scalaz.Unapply

object applicative2 {
  implicit def applicativeOps[FA](v: FA)(implicit F0: Unapply[Applicative, FA]) = new ApplicativeOps_[F0.M,F0.A](F0(v))(F0.TC)
}

class ApplicativeOps_[F[_], A](fa: F[A])(implicit F: Applicative[F]){
  def *>[B](fb: F[B]): F[B] = F.*>(fa, fb)
  def <*[B](fb: F[B]): F[A] = F.<*(fa, fb)

  def map2[B, C](fb: F[B])(f: (A, B) => C): F[C] = F.map2(fa, fb)(f)
  def map3[B, C, D](fb: F[B], fc: F[C])(f: (A, B, C) => D): F[D] = F.map3(fa, fb, fc)(f)

  def tuple2[B](fb: F[B]): F[(A, B)] = F.tuple2(fa, fb)
  def tuple3[B, C](fb: F[B], fc: F[C]): F[(A, B, C)] = F.tuple3(fa, fb, fc)
}
