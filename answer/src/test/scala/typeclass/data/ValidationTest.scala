package typeclass.data

import typeclass.ApplicativeLaws
import typeclass.Prelude._
import typeclass.instances.list._

import scalaprops.Scalaprops

object ValidationTest extends Scalaprops {

  val applicative = ApplicativeLaws[Validation[List[Int], ?]].all
}
