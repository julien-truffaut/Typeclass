package typeclass.data

import typeclass.Prelude._
import typeclass.{ApplicativeLaws, FoldableLaws}

import scalaprops.Scalaprops

object ConstTest extends Scalaprops {

  val applicative = ApplicativeLaws[Const[List[Int], ?]].all
  val foldable = FoldableLaws[Const[Boolean, ?]].all

}
