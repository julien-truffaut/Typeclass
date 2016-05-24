package typeclass

import typeclass.data.Either

import scalaprops.Scalaprops

object EitherTest extends Scalaprops {

  val monad = MonadLaws[Either[Int, ?]].all

}
