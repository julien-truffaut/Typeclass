package typeclass.syntax

import typeclass.Apply

object apply {
  implicit def applyOps[F[_]: Apply, A](fa: F[A]): ApplyOps[F, A] = new ApplyOps(fa)
  implicit def applyOps2[F[_]: Apply, A, B](fab: F[A => B]): Apply2Ops[F, A, B] = new Apply2Ops(fab)
}

class ApplyOps[F[_], A](fa: F[A])(implicit F: Apply[F]){
  def *>[B](fb: F[B]): F[B] = F.*>(fa, fb)
  def <*[B](fb: F[B]): F[A] = F.<*(fa, fb)

  def map2[B, C](fb: F[B])(f: (A, B) => C): F[C] = F.map2(fa, fb)(f)
  def map3[B, C, D](fb: F[B], fc: F[C])(f: (A, B, C) => D): F[D] = F.map3(fa, fb, fc)(f)

  def tuple2[B](fb: F[B]): F[(A, B)] = F.tuple2(fa, fb)
  def tuple3[B, C](fb: F[B], fc: F[C]): F[(A, B, C)] = F.tuple3(fa, fb, fc)
}

class Apply2Ops[F[_], A, B](fab: F[A => B])(implicit F: Apply[F]){
  def ap(fa: F[A]): F[B] = F.ap(fab, fa)
}