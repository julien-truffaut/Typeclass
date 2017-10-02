package typeclass.data

import typeclass.FunctorLaws
import typeclass.instances.list._

import scalaprops.Scalaprops

object ZipStreamTest extends Scalaprops {
  // applicative fails because pure[ZipStream[A]] == pure[ZipStream[A]] does not terminate
  val functor = FunctorLaws[ZipStream].all
}
