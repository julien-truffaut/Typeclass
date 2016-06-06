package typeclass

import typeclass.Prelude._
import typeclass.data.{List, Option}
import typeclass.data.Option.{some, none}

trait Foldable[F[_]] {
  def foldLeft[A, B] (fa: F[A], z: B)(f: (B, A) => B): B
  def foldRight[A, B](fa: F[A], z: B)(f: (A, B) => B): B

  def foldMap[A, B](fa: F[A])(f: A => B)(implicit B: Monoid[B]): B =
    foldLeft(fa, B.empty)((acc, a) => B.combine(acc, f(a)))

  def fold[A](fa: F[A])(implicit A: Monoid[A]): A =
    foldMap(fa)(identity)

  def size[A](fa: F[A]): Int =
    foldLeft(fa, 0)((acc, _) => acc + 1)

  def headOption[A](fa: F[A]): Option[A] =
    foldRight(fa, none[A])((a, _) => some(a))

  def tailOption[A](fa: F[A]): Option[A] =
    foldRight(fa, none[A])((a, acc) => acc.orElse(some(a)))

  def maximum[A](fa: F[A])(implicit A: Ordering[A]): Option[A] =
    foldLeft(fa, none[A])((acc, a) => acc.fold(some(a), other =>
      if(A.gt(a, other)) some(a) else some(other)
    ))

  def minimum[A](fa: F[A])(implicit A: Ordering[A]): Option[A] =
    maximum(fa)(A.reverse)

  def toList[A](fa: F[A]): List[A] =
    foldLeft(fa, List.nil[A])((acc, a) => List.cons(a, acc)).reverse

  def forAll[A](fa: F[A], p: A => Boolean): Boolean =
    foldRight(fa, true)((a, acc) => p(a) && acc)

  def exist[A](fa: F[A], p: A => Boolean): Boolean =
    foldRight(fa, false)((a, acc) => p(a) || acc)
}

object Foldable {
  /** syntax to summon an Foldable instance using Foldable[Foo] instead of implicitly[Foldable[Foo]] */
  def apply[F[_]](implicit ev: Foldable[F]): Foldable[F] = ev
}

/** All functor instance must respect the following laws */
case class FoldableLaws[F[_]](implicit F: Foldable[F]) {
  import typeclass.syntax.applicative._
  import typeclass.syntax.foldable._
  import scalaprops.Gen
  import scalaprops.Properties.properties
  import scalaprops.Property.forAll
  import scalaz.std.string._

  def sizeConsistentWithToList[A](implicit genFA: Gen[F[A]]) =
    forAll((fa: F[A]) => fa.size == fa.toList.size)

  def foldLeftConsistentWithFoldMap[A](implicit genFA: Gen[F[A]]) =
    forAll((fa: F[A]) => fa.foldMap(_.pure[List]) == fa.foldLeft(List.nil[A])(_ :+ _))

  def foldRightConsistentWithFoldMap[A](implicit genFA: Gen[F[A]]) =
    forAll((fa: F[A]) => fa.foldMap(_.pure[List]) == fa.foldRight(List.nil[A])(_ +: _))

  def all(implicit genFI: Gen[F[Int]], genF: Gen[Int => Int]) =
    properties("Foldable")(
      ("sizeConsistentWithToList", sizeConsistentWithToList[Int]),
      ("foldLeftConsistentWithFoldMap", foldLeftConsistentWithFoldMap[Int]),
      ("foldRightConsistentWithFoldMap", foldRightConsistentWithFoldMap[Int])
    )
}