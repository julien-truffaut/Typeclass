package typeclass.data

import typeclass.{ApplicativeLaws, FunctorLaws}
import typeclass.Prelude._
import typeclass.std.list._

import scalaprops.Scalaprops

object ConstTest extends Scalaprops {

  // Why there is no ambiguity???
  val functor = FunctorLaws[Const[List[Int], ?]].all
  val applicative = ApplicativeLaws[Const[List[Int], ?]].all

}
