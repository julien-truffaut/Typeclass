package typeclass

import typeclass.data.{ConsList, Id}

import scalaprops.Scalaprops

object ApplicativeTest extends Scalaprops {

  val id = ApplicativeLaws[Id].all
  val consList = ApplicativeLaws[ConsList].all

  // check syntax compile
  import typeclass.syntax.applicative._

  Id(3).map2(Id(2))(_ + _) // Id(5)
  3.pure[Id] // Id(3)
  Id(0).ap(Id((i: Int) => i + 1)) // Id(1)

}
