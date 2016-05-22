package typeclass

trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]

  def void[A](fa: F[A]): F[Unit] = map(fa)(_ => ())
  def as[A, B](fa: F[A], b: B): F[B] = map(fa)(_ => b)
  def lift[A, B](f: A => B): F[A] => F[B] = map(_)(f)
}

object Functor {
  /** syntax to summon a Functor instance using Functor[Foo] instead of implicitly[Functor[Foo]] */
  def apply[F[_]](implicit ev: Functor[F]): Functor[F] = ev
}

/** All functor instance must respect the following laws */
case class FunctorLaws[F[_]](implicit F: Functor[F]) {
  import scalaprops.Property.forAll
  import scalaprops.Properties.properties
  import scalaprops.Gen
  import scalaz.std.string._

  def mapId[A](implicit genFA: Gen[F[A]]) =
    forAll((fa: F[A]) => F.map(fa)(identity) == fa)

  def mapFusion[A, B, C](implicit genFA: Gen[F[A]], genAB: Gen[A => B], genBC: Gen[B => C]) =
    forAll((fa: F[A], f: A => B, g: B => C) =>
      F.map(F.map(fa)(f))(g) == F.map(fa)(g compose f)
    )

  def all(implicit genFI: Gen[F[Int]], genF: Gen[Int => Int]) =
    properties("Functor")(
      "mapId"     -> mapId[Int],
      "mapFusion" -> mapFusion[Int, Int, Int]
    )
}
