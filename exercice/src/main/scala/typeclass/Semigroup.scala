package typeclass

trait Semigroup[A] {
  def combine(x: A, y: A): A
}

object Semigroup {
  def apply[A](implicit ev: Semigroup[A]): Semigroup[A] = ev

  object ops {
    implicit class SemigroupOps[A](a: A)(implicit A: Semigroup[A]){
      def combine(other: A): A = A.combine(a, other)
    }
  }

  implicit val stringSemigroup: Semigroup[String] = new Semigroup[String] {
    override def combine(x: String, y: String): String = x ++ y
  }

  implicit val intSemigroup: Semigroup[Int] = new Semigroup[Int] {
    override def combine(x: Int, y: Int): Int = ???
  }

  implicit def listSemigroup[A]: Semigroup[List[A]] = ???
}


case class SemigroupLaws[A](implicit A: Semigroup[A]) {
  import Semigroup.ops._
  import scalaprops.Properties.properties
  import scalaprops.Property.forAll
  import scalaprops.{Gen, Property}
  import scalaz.std.string._

  def associative(implicit genA: Gen[A]): Property =
    forAll((x: A, y: A, z: A) => x.combine(y).combine(z) == x.combine(y.combine(z)))

  def all(implicit genA: Gen[A]) =
    properties("Semigroup")(("associative", associative))
}