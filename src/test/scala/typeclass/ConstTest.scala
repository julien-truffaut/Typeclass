package typeclass

import typeclass.Prelude._
import typeclass.data.{Const, List}

import scalaprops.Scalaprops

object ConstTest extends Scalaprops {

  val applicative = ApplicativeLaws[Const[List[Int], ?]].all
  val foldable = FoldableLaws[Const[Boolean, ?]].all

}
