package polymorphism

import scala._
import scala.Predef._

trait CharacterV4[A] {
  def healthPoint(a: A): Int
  def setHealthPoint(a: A, n: Int): A
}

object CharacterV4 {

  final case class Wizard(name: String, healthPoint: Int, manaPoint: Int)
  object Wizard {
    implicit val character: CharacterV4[Wizard] = new CharacterV4[Wizard] {
      def healthPoint(a: Wizard): Int = a.healthPoint
      def setHealthPoint(a: Wizard, n: Int): Wizard = a.copy(healthPoint = n)
    }
  }

  final case class Barbarian(name: String, healthPoint: Int, enraged: Boolean)
  object Barbarian {
    implicit val character: CharacterV4[Barbarian] = new CharacterV4[Barbarian] {
      def healthPoint(a: Barbarian): Int = a.healthPoint
      def setHealthPoint(a: Barbarian, n: Int): Barbarian = a.copy(healthPoint = n)
    }
  }

}

object GameAppV4 extends App {
  import CharacterV4._

  val eliot = Wizard("Eliot", healthPoint = 5, manaPoint = 10)
  val raoul = Barbarian("Raoul", healthPoint = 20, enraged = false)

  implicitly[CharacterV4[Wizard]].healthPoint(eliot) // 5

  eliot.setHealthPoint(3) // Wizard("Eliot", healthPoint = 3, manaPoint = 10))

  implicit class CharacterOps[A](a: A)(implicit ev: CharacterV4[A]){
    def healthPoint: Int = ev.healthPoint(a)
    def setHealthPoint(n: Int): A = ev.setHealthPoint(a, n)
  }
}

