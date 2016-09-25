package typeclass.data

import typeclass.Functor
import typeclass.Prelude._

import scalaprops.Gen

sealed trait Bar[A]

object Bar {
  case class Bar0[A]() extends Bar[A]
  case class Bar1[A](i: Int, a: A) extends Bar[A]
  case class Bar2[A](a1: A, c: Char, a2: A) extends Bar[A]

  implicit val functor: Functor[Bar] = new Functor[Bar] {
    def map[A, B](fa: Bar[A])(f: A => B): Bar[B] = fa match {
      case Bar0()          => Bar0()
      case Bar1(i, a)      => Bar1(i, f(a))
      case Bar2(a1, c, a2) => Bar2(f(a1), c, f(a2))
    }
  }

  implicit def gen[A: Gen]: Gen[Bar[A]] = Gen.oneOf(
    Gen.elements(Bar0()),
    for {
      i <- Gen.choose(0, 100)
      a <- Gen[A]
    } yield Bar1(i, a),
    for {
      a1 <- Gen[A]
      c  <- Gen.alphaChar
      a2 <- Gen[A]
    } yield Bar2(a1, c, a2)
  )
}