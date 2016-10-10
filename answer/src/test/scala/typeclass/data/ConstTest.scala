package typeclass.data

import typeclass.{ApplicativeLaws, ApplyLaws, FunctorLaws}
import typeclass.Prelude._
import typeclass.std.list._

import scalaprops.Scalaprops

object ConstTest extends Scalaprops {

  val functor = FunctorLaws[Const[List[Int], ?]].all
  val apply = ApplyLaws[Const[List[Int], ?]].all
  val applicative = ApplicativeLaws[Const[List[Int], ?]].all

}
