package typeclass

import typeclass.Prelude._

trait Applicative[F[_]] extends Functor[F]{
  def pure[A](a: A): F[A]
  def ap[A, B](fab: F[A => B], fa: F[A]): F[B]

  def map[A, B](fa: F[A])(f: A => B): F[B] =
    ap(pure(f), fa)

  def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] =
    ap(map(fa)((a: A) => (b: B) => f(a, b)), fb)

  def map3[A, B, C, D](fa: F[A], fb: F[B], fc: F[C])(f: (A, B, C) => D): F[D] =
    ap(map2(fa, fb)((a, b) => (c: C) => f(a, b, c)), fc)

  def *>[A, B](fa: F[A], fb: F[B]): F[B] =
    map2(fa, fb)((_, b) => b)

  def <*[A, B](fa: F[A], fb: F[B]): F[A] =
    map2(fa, fb)((a, _) => a)

  def forever[A](fa: F[A]): F[A] =
    *>(fa, forever(fa))

  def tuple2[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
    map2(fa, fb)((_, _))

  def tuple3[A, B, C](fa: F[A], fb: F[B], fc: F[C]): F[(A, B, C)] =
    map3(fa, fb, fc)((_, _, _))

  def lift2[A, B, C](f: (A, B) => C): (F[A], F[B]) => F[C] =
    map2(_, _)(f)

  def lift3[A, B, C, D](f: (A, B, C) => D): (F[A], F[B], F[C]) => F[D] =
    map3(_, _, _)(f)
}

object Applicative {
  def apply[F[_]](implicit ev: Applicative[F]): Applicative[F] = ev
}

case class ApplicativeLaws[F[_]](implicit F: Applicative[F]) {
  import typeclass.syntax.applicative._
  import typeclass.syntax.functor._

  import scalaprops.{Gen, Properties, Property}
  import scalaprops.Properties.properties
  import scalaprops.Property.forAll
  import scalaz.std.string._

  def liftFunction[A, B](implicit genA: Gen[A], genAB: Gen[A => B]): Property =
    forAll((a: A, f: A => B) =>
      f.pure.ap(a.pure) == f(a).pure
    )

  def apId[A](implicit genFA: Gen[F[A]]): Property =
    forAll((fa: F[A]) =>
      (identity[A] _).pure.ap(fa) == fa
    )

  def consistentMap[A, B](implicit genA: Gen[F[A]], genAB: Gen[A => B]): Property =
    forAll((fa: F[A], f: A => B) =>
      f.pure.ap(fa) == fa.map(f)
    )

  def laws(implicit genFI: Gen[F[Int]], genF: Gen[Int => Int]): Properties[String] =
    properties("Applicative")(
      ("liftFunction", liftFunction[Int, Int]),
      ("apId"         , apId[Int]),
      ("consistentMap", consistentMap[Int, Int])
    )

  def all(implicit genFI: Gen[F[Int]], genF: Gen[Int => Int]): Properties[String] =
    Properties.fromProps("Applicative-all", FunctorLaws[F].all, laws)
}
