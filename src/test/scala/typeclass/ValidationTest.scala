package typeclass

import typeclass.Prelude._
import typeclass.data.{List, Validation}

import scalaprops.Scalaprops

object ValidationTest extends Scalaprops {

  val applicative = ApplicativeLaws[Validation[List[Int], ?]].all
  val foldable = FoldableLaws[Validation[Boolean, ?]].all
}
