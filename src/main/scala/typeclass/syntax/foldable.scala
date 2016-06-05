package typeclass.syntax

import typeclass.{Monoid, Foldable}
import typeclass.Prelude._
import typeclass.data.{List, Option}
import typeclass.data.Option._

object foldable {
  /** pimp F[A] with all methods of FoldableOps if F has an instance of Foldable */
  implicit def foldableOps[F[_], A](fa: F[A])(implicit F: Foldable[F]): FoldableOps[F, A] = new FoldableOps(fa)
}

class FoldableOps[F[_], A](fa: F[A])(implicit F: Foldable[F]){
  def foldLeft[B] (z: B)(f: (B, A) => B): B = F.foldLeft(fa, z)(f)
  def foldRight[B](z: B)(f: (A, B) => B): B = F.foldRight(fa, z)(f)
  def foldMap[B](f: A => B)(implicit B: Monoid[B]): B = F.foldMap(fa)(f)
  def fold(implicit A: Monoid[A]): A = F.fold(fa)
  def size: Int = F.size(fa)
  def headOption: Option[A] = F.headOption(fa)
  def tailOption: Option[A] = F.tailOption(fa)
  def maximum(implicit A: Ordering[A]): Option[A] = F.maximum(fa)
  def minimum(implicit A: Ordering[A]): Option[A] = F.minimum(fa)
  def toList: List[A] = F.toList(fa)
  def forAll(p: A => Boolean): Boolean = F.forAll(fa, p)
  def exist(p: A => Boolean): Boolean = F.exist(fa, p)
}
