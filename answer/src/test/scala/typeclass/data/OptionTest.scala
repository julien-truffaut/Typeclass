package typeclass.data

import typeclass.std.option._
import typeclass.std.string._
import typeclass.{MonadLaws, MonoidLaws}

import scalaprops.{Gen, Scalaprops}

object OptionTest extends Scalaprops {
  implicit val stringGen: Gen[String] = Gen.asciiString

  val monad = MonadLaws[Option].all
  val monoid = MonoidLaws[Option[String]].all

}
