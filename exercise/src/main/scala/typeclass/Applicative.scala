package typeclass

trait Applicative[F[_]] extends Functor[F]{
  def pure[A](a: A): F[A]
  def ap[A, B](fab: F[A => B], fa: F[A]): F[B]

  def map[A, B](fa: F[A])(f: A => B): F[B] = ???
  def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] = ???
  def map3[A, B, C, D](fa: F[A], fb: F[B], fc: F[C])(f: (A, B, C) => D): F[D] = ???
  def *>[A, B](fa: F[A], fb: F[B]): F[B] = ???
  def <*[A, B](fa: F[A], fb: F[B]): F[A] = ???
  def forever[A](fa: F[A]): F[A] = ???
  def tuple2[A, B](fa: F[A], fb: F[B]): F[(A, B)] = ???
  def tuple3[A, B, C](fa: F[A], fb: F[B], fc: F[C]): F[(A, B, C)] = ???
  def lift2[A, B, C](f: (A, B) => C): (F[A], F[B]) => F[C] = ???
  def lift3[A, B, C, D](f: (A, B, C) => D): (F[A], F[B], F[C]) => F[D] = ???
}

object Applicative {
  def apply[F[_]](implicit ev: Applicative[F]): Applicative[F] = ev
}

case class ApplicativeLaws[F[_]](implicit ev: Applicative[F]) {
  import typeclass.syntax.applicative._
  import typeclass.syntax.functor._

  import scalaprops.{Gen, Properties, Property}
  import scalaprops.Properties.properties
  import scalaprops.Property.forAll
  import scalaz.std.string._

  def liftFunction[A, B](implicit genA: Gen[A], genAB: Gen[A => B]): Property = ???
  def apId[A](implicit genFA: Gen[F[A]]): Property = ???
  def consistentMap[A, B](implicit genA: Gen[F[A]], genAB: Gen[A => B]): Property = ???

  def laws(implicit genFI: Gen[F[Int]], genF: Gen[Int => Int]): Properties[String] =
    properties("Applicative")(
      ("liftFunction", liftFunction[Int, Int]),
      ("apId"         , apId[Int]),
      ("consistentMap", consistentMap[Int, Int])
    )

  def all(implicit genFI: Gen[F[Int]], genF: Gen[Int => Int]): Properties[String] =
    Properties.fromProps("Applicative-all", FunctorLaws[F].all, laws)
}
