package typeclass

import scala.App
import typeclass.data.List._
import typeclass.data.{Id, List}
import typeclass.Prelude._
import typeclass.syntax.applicative._
import typeclass.syntax.functor._
import typeclass.syntax.monad._
import typeclass.syntax.semigroup._
import typeclass.syntax.foldable._

object Example extends App {

  val list = cons(1, cons(2, cons(3, nil)))

  println(list)

  println(list.map(_ + 1))
  println(list.foldLeft(0)(_ + _))
  println(1.pure[List])
  println(list.flatMap(i => cons(i-1, cons(i+1, nil))))
  println(list combine list.reverse)

  println(Id(Id(1)).flatten)

}
