package typeclass

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]

  def void[A](fa: F[A]): F[Unit] = ???
  def as[A, B](fa: F[A], b: B): F[B] = ???
  def lift[A, B](f: A => B): F[A] => F[B] = ???
}

object Functor {
  def apply[F[_]](implicit ev: Functor[F]): Functor[F] = ev
}

case class FunctorLaws[F[_]](implicit F: Functor[F]) {
  import typeclass.syntax.functor._
  import scalaprops.Gen
  import scalaprops.Properties.properties
  import scalaprops.Property.forAll
  import scalaz.std.string._

  def mapId[A](implicit genFA: Gen[F[A]]) = ???

  def mapFusion[A, B, C](implicit genFA: Gen[F[A]], genAB: Gen[A => B], genBC: Gen[B => C]) = ???

  def all(implicit genFI: Gen[F[Int]], genF: Gen[Int => Int]) =
    properties("Functor")(
      ("mapId"    , mapId[Int]),
      ("mapFusion", mapFusion[Int, Int, Int])
    )
}
