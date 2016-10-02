package typeclass.data

import typeclass.std.string._
import typeclass.{FoldableLaws, MonadLaws, MonoidLaws}

import scalaprops.{Gen, Scalaprops}

object OptionTest extends Scalaprops {
  implicit val stringGen: Gen[String] = Gen.asciiString

  val monad = MonadLaws[Option].all
  val foldable = FoldableLaws[Option].all
  val monoid = MonoidLaws[Option[String]].all

}
