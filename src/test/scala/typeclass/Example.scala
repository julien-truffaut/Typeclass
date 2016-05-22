package typeclass

import typeclass.data.ConsList._
import typeclass.syntax.functor._

object Example extends App {

  val list = cons(1, cons(2, cons(3, nil)))

  println(list)

  println(list.map(_ + 1))
  println(list.foldLeft(0)(_ + _))
  println(list.reverse)

}
