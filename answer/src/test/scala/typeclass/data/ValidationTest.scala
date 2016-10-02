package typeclass.data

import typeclass.Prelude._
import typeclass.{ApplicativeLaws, FoldableLaws}

import scalaprops.Scalaprops

object ValidationTest extends Scalaprops {

  val applicative = ApplicativeLaws[Validation[List[Int], ?]].all
  val foldable = FoldableLaws[Validation[Boolean, ?]].all
}
