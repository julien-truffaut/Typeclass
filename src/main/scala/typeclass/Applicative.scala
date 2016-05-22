package typeclass

trait Applicative[F[_]] {
  def functor: Functor[F]

  def pure[A](a: A): F[A]
  def ap[A, B](fab: F[A => B], fa: F[A]): F[B]

  def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] =
    ap(functor.map(fa)((a: A) => (b: B) => f(a, b)), fb)

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
  /** syntax to summon an Applicative instance using Applicative[Foo] instead of implicitly[Applicative[Foo]] */
  def apply[F[_]](implicit ev: Applicative[F]): Applicative[F] = ev
}

/** All Applicative instance must respect the following laws */
case class ApplicativeLaws[F[_]](implicit F: Applicative[F]) {
  import scalaprops.Property.forAll
  import scalaprops.Properties.properties
  import scalaprops.Gen
  import scalaz.std.string._

  def liftFunction[A, B](implicit genA: Gen[A], genAB: Gen[A => B]) =
    forAll((a: A, f: A => B) =>
      F.ap(F.pure(f), F.pure(a)) == F.pure(f(a))
    )

  def apId[A](implicit genFA: Gen[F[A]]) =
    forAll((fa: F[A]) =>
      F.ap(F.pure(identity[A](_)), fa) == fa
    )

  def consistentMap[A, B](implicit genA: Gen[F[A]], genAB: Gen[A => B]) =
    forAll((fa: F[A], f: A => B) =>
      F.ap(F.pure(f), fa) == F.functor.map(fa)(f)
    )

  def all(implicit genFI: Gen[F[Int]], genF: Gen[Int => Int]) =
    properties("Applicative")(
      "liftFunction"  -> liftFunction[Int, Int],
      "apId"          -> apId[Int],
      "consistentMap" -> consistentMap[Int, Int]
    )
}
