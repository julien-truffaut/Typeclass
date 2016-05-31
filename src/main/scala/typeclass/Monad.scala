package typeclass

import typeclass.Prelude._

import scalaprops.Properties

trait Monad[F[_]] extends Applicative[F]{
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def flatten[A](ffa: F[F[A]]): F[A] = flatMap(ffa)(identity)

  def ap[A, B](fab: F[A => B], fa: F[A]): F[B] =
    flatMap(fab)(map(fa)(_))

  override def map[A, B](fa: F[A])(f: A => B): F[B] = flatMap(fa)(f andThen pure)
}

object Monad {
  /** syntax to summon an Monad instance using Monad[Foo] instead of implicitly[Monad[Foo]] */
  def apply[F[_]](implicit ev: Monad[F]): Monad[F] = ev
}

/** All Monad instance must respect the following laws */
case class MonadLaws[F[_]](implicit F: Monad[F]) {
  import typeclass.syntax.applicative._
  import typeclass.syntax.functor._
  import typeclass.syntax.monad._

  import scalaprops.Gen
  import scalaprops.Properties.properties
  import scalaprops.Property.forAll
  import scalaz.std.string._

  def consistentAp[A, B](implicit genA: Gen[F[A]], genAB: Gen[F[A => B]]) =
    forAll((ff: F[A => B], fa: F[A]) =>
      ff.flatMap(f => fa.map(f)) == ff.ap(fa)
    )

  def consistentMap[A, B](implicit genA: Gen[F[A]], genAB: Gen[A => B]) =
    forAll((fa: F[A], f: A => B) =>
      fa.flatMap(a => f(a).pure) == fa.map(f)
    )

  def flatMapAssociative[A, B, C](implicit genA: Gen[F[A]], genAB: Gen[A => F[B]], genBC: Gen[B => F[C]]) =
    forAll((fa: F[A], f: A => F[B], g: B => F[C]) =>
      fa.flatMap(f).flatMap(g) == fa.flatMap(a => f(a).flatMap(g))
    )

  def leftIdentity[A, B](implicit genA: Gen[A], genAB: Gen[A => F[B]]) =
    forAll((a: A, f: A => F[B]) =>
      a.pure.flatMap(f) == f(a)
    )

  def rightIdentity[A](implicit genA: Gen[F[A]]) =
    forAll((fa: F[A]) =>
      fa.flatMap(_.pure) == fa
    )

  def laws(implicit genFI: Gen[F[Int]], genFFI: Gen[F[Int => Int]]): Properties[String] =
    properties("Monad")(
      ("consistentAp"       , consistentAp[Int, Int]),
      ("consistentMap"      , consistentMap[Int, Int]),
      ("flatMapAssociative" , flatMapAssociative[Int, Int, Int]),
      ("leftIdentity"       , leftIdentity[Int, Int]),
      ("rightIdentity"      , rightIdentity[Int])
    )

  def all(implicit genFI: Gen[F[Int]], genFFI: Gen[F[Int => Int]]): Properties[String] =
    Properties.fromProps("Monad-all", ApplicativeLaws[F].all, laws)

}