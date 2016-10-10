package typeclass

import typeclass.Prelude._

trait Applicative[F[_]] extends Apply[F]{
  override def toString: String = "Applicative"

  def pure[A](a: A): F[A]

  def map[A, B](fa: F[A])(f: A => B): F[B] =
    ap(pure(f), fa)
}

object Applicative {
  def apply[F[_]](implicit ev: Applicative[F]): Applicative[F] = ev
}

case class ApplicativeLaws[F[_]](implicit F: Applicative[F]) {
  import typeclass.syntax.applicative._
  import typeclass.syntax.apply._
  import typeclass.syntax.functor._

  import scalaprops.Properties.properties
  import scalaprops.Property.forAll
  import scalaprops.{Gen, Properties, Property}
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

  def all(implicit genFI: Gen[F[Int]], genII: Gen[Int => Int], genFII: Gen[F[Int => Int]]): Properties[String] =
    Properties.fromProps("Applicative-all", ApplyLaws[F].all, laws)
}
