package typeclass.data

import typeclass.Functor

import scalaprops.Gen

case class Const[A, B](value: A){
  def retag[C]: Const[A, C] = Const(value)
}

object Const {
  implicit def constFunctor[R]: Functor[Const[R, ?]] = new Functor[Const[R, ?]] {
    def map[A, B](fa: Const[R, A])(f: (A) => B): Const[R, B] = ???
  }

  implicit def gen[A: Gen, B]: Gen[Const[A, B]] = Gen[A].map(Const(_))
}
