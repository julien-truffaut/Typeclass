package typeclass.data

import typeclass.MonadLaws
import typeclass.Prelude._

import scalaprops.Scalaprops

object EitherTest extends Scalaprops {

  val monad = MonadLaws[Either[Boolean, ?]].all
}
