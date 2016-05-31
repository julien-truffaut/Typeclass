package typeclass

import typeclass.Prelude._
import typeclass.data.Either

import scalaprops.Scalaprops

object EitherTest extends Scalaprops {

  val monad = MonadLaws[Either[Boolean, ?]].all

}
