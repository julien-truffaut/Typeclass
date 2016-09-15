package polymorphism

import scala._
import scala.Predef._

trait CharacterV3[A <: CharacterV3[A]] { self: A =>
  def name: String
  def healthPoint: Int

  def setHealthPoint(n: Int): A
}

object CharacterV3 {

  final case class Wizard(name: String, healthPoint: Int, manaPoint: Int) extends CharacterV3[Wizard] {
    def setHealthPoint(n: Int): Wizard = copy(healthPoint = n)
  }

  final case class Barbarian(name: String, healthPoint: Int, enraged: Boolean) extends CharacterV3[Barbarian] {
    def setHealthPoint(n: Int): Barbarian = copy(healthPoint = n)
  }

  def _setHealthPoint[A <: CharacterV2[A]](a: A, n: Int): A =
    a.setHealthPoint(n)

  // self-type Rogue does not conform to Character[Barbarian]
  //case class Rogue(name: String, healthPoint: Int) extends Character[Barbarian] {
  //  def setHealthPoint(n: Int): Barbarian =
  //    Barbarian(name, healthPoint = n, enraged = false)
  //}

}

object GameAppV3 extends App {
  import CharacterV3._

  val eliot = Wizard("Eliot", healthPoint = 5, manaPoint = 10)
  val raoul = Barbarian("Raoul", healthPoint = 20, enraged = false)

  println(eliot.setHealthPoint(3)) // Wizard("Eliot", healthPoint = 3, manaPoint = 10))
}
