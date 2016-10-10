package typeclass.syntax

import typeclass.Applicative

object applicative {
  implicit def applicativeOps[A](a: A): ApplicativeOps[A] = new ApplicativeOps(a)
}

class ApplicativeOps[A](a: A){
  def pure[F[_]](implicit F: Applicative[F]) = F.pure(a)
}
