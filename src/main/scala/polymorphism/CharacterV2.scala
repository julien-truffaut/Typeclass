package polymorphism

import scala._
import scala.Predef._

trait CharacterV2[A <: CharacterV2[A]] {
  def name: String
  def healthPoint: Int

  def setHealthPoint(n: Int): A
}

object CharacterV2 {

  case class Wizard(name: String, healthPoint: Int, manaPoint: Int) extends CharacterV2[Wizard] {
    def setHealthPoint(n: Int): Wizard = copy(healthPoint = n)
  }

  case class Barbarian(name: String, healthPoint: Int, enraged: Boolean) extends CharacterV2[Barbarian] {
    def setHealthPoint(n: Int): Barbarian = copy(healthPoint = n)
  }

  def _setHealthPoint[A <: CharacterV2[A]](a: A, n: Int): A =
    a.setHealthPoint(n)

  case class Rogue(name: String, healthPoint: Int) extends CharacterV2[Barbarian] {
    def setHealthPoint(n: Int): Barbarian =
      Barbarian(name, healthPoint = n, enraged = false) // WTF
  }

}

object GameAppV2 extends App {
  import CharacterV2._

  val eliot = Wizard("Eliot", healthPoint = 5, manaPoint = 10)
  val raoul = Barbarian("Raoul", healthPoint = 20, enraged = false)

  println(eliot.setHealthPoint(3)) // Wizard("Eliot", healthPoint = 3, manaPoint = 10))
}


