package typeclass.data

import typeclass.{Applicative, Functor, Monoid}

import scalaprops.Gen

case class Const[A, B](value: A){
  def retag[C]: Const[A, C] = Const(value)
}

object Const {
  implicit def constFunctor[R]: Functor[Const[R, ?]] = new Functor[Const[R, ?]] {
    def map[A, B](fa: Const[R, A])(f: A => B): Const[R, B] = fa.retag[B]
  }

  implicit def constApplicative[R](implicit R: Monoid[R]): Applicative[Const[R, ?]] = new Applicative[Const[R, ?]] {
    def pure[A](a: A): Const[R, A] = Const(R.empty)
    def ap[A, B](fab: Const[R, A => B], fa: Const[R, A]): Const[R, B] =
      Const(R.combine(fab.value, fa.value))
  }

  implicit def gen[A: Gen, B]: Gen[Const[A, B]] = Gen[A].map(Const(_))
}
