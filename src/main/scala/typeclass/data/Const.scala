package typeclass.data

import typeclass.{Foldable, Applicative, Monoid}

import scalaprops.Gen

case class Const[A, B](value: A){
  def retag[C]: Const[A, C] = Const(value)
}

object Const {
  implicit def applicative[R](implicit R: Monoid[R]): Applicative[Const[R, ?]] = new Applicative[Const[R, ?]] {
    def pure[A](a: A): Const[R, A] = Const(R.empty)
    def ap[A, B](fab: Const[R, A => B], fa: Const[R, A]): Const[R, B] =
      Const(R.combine(fab.value, fa.value))
  }

  implicit def foldable[R]: Foldable[Const[R, ?]] = new Foldable[Const[R, ?]] {
    def foldLeft[A, B](fa: Const[R, A], z: B)(f: (B, A) => B): B = z
    def foldRight[A, B](fa: Const[R, A], z: B)(f: (A, B) => B): B = z
  }

  implicit def gen[A: Gen, B]: Gen[Const[A, B]] = Gen[A].map(Const(_))
}