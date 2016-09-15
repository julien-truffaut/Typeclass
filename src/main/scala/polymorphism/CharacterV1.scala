package polymorphism

import scala._
import scala.Predef._

trait CharacterV1 {
  def name: String
  def healthPoint: Int

  def setHealthPoint(n: Int): CharacterV1
}

object CharacterV1 {

  case class Wizard(name: String, healthPoint: Int, manaPoint: Int) extends CharacterV1 {
    def setHealthPoint(n: Int): CharacterV1 = copy(healthPoint = n)
  }

  case class Barbarian(name: String, healthPoint: Int, enraged: Boolean) extends CharacterV1 {
    def setHealthPoint(n: Int): CharacterV1 = copy(healthPoint = n)
  }

  case class Rogue(name: String, healthPoint: Int) extends CharacterV1 {
    def setHealthPoint(n: Int): CharacterV1 =
      Barbarian(name, healthPoint = n, enraged = false) // WTF
  }

  // Error: found: Character, required: A
  // def _setHealthPoint[A <: Character](a: A, n: Int): A = a.setHealthPoint(n)

}

object GameAppV1 extends App {
  import CharacterV1._

  val eliot = Wizard("Eliot", healthPoint = 5, manaPoint = 10)
  val raoul = Barbarian("Raoul", healthPoint = 20, enraged = false)

  println(eliot.setHealthPoint(3)) // Wizard("Eliot", healthPoint = 3, manaPoint = 10))
}

