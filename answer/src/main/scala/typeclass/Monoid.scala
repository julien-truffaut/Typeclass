package typeclass

import typeclass.Prelude._

import scalaprops.Properties

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  def apply[A](implicit ev: Monoid[A]): Monoid[A] = ev
}

case class MonoidLaws[A](implicit A: Monoid[A]) {
  import typeclass.syntax.semigroup._

  import scalaprops.Gen
  import scalaprops.Properties.properties
  import scalaprops.Property.forAll
  import scalaz.std.string._

  def leftIdentity(implicit genA: Gen[A]) =
    forAll((x: A) => A.empty.combine(x) == x)

  def rightIdentity(implicit genA: Gen[A]) =
    forAll((x: A) => x.combine(A.empty) == x)

  def laws(implicit genA: Gen[A]) =
    properties("Monoid")(
      ("leftIdentity" , leftIdentity),
      ("rightIdentity", rightIdentity)
    )

  def all(implicit genA: Gen[A]): Properties[String] =
    Properties.fromProps("Monoid-all", SemigroupLaws[A].all, laws)
}