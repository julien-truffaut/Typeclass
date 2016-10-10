package typeclass

trait Apply[F[_]] extends Functor[F]{

  override def toString: String = "Apply"

  def ap[A, B](fab: F[A => B], fa: F[A]): F[B]

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

object Apply {
  def apply[F[_]](implicit ev: Apply[F]): Apply[F] = ev
}

case class ApplyLaws[F[_]](implicit F: Apply[F]) {
  import typeclass.syntax.apply._
  import typeclass.syntax.functor._

  import scalaprops.{Gen, Properties, Property}
  import scalaprops.Properties.properties
  import scalaprops.Property.forAll
  import scalaz.std.string._

  def apComposition[A, B, C](implicit genA: Gen[F[A]], genAB: Gen[F[A => B]], genBC: Gen[F[B => C]]): Property =
    forAll((fa: F[A], f: F[A => B], g: F[B => C]) =>
      g.ap(f.ap(fa)) == f.map(ab => (bc: B => C) => bc compose ab).ap(g).ap(fa)
    )

  def laws(implicit genFI: Gen[F[Int]], genF: Gen[F[Int => Int]]): Properties[String] =
    properties("Apply")(
      ("apComposition", apComposition[Int, Int, Int])
    )

  def all(implicit genFI: Gen[F[Int]], genF: Gen[F[Int => Int]]): Properties[String] =
    Properties.fromProps("Apply-all", FunctorLaws[F].all, laws)
}
