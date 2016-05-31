package typeclass

object Prelude {

  type Nothing = scala.Nothing
  type Any = scala.Any

  type Unit = scala.Unit
  type Boolean = scala.Boolean
  type Char = scala.Char
  type Int = scala.Int
  type String = java.lang.String

  type <:<[-A, +B] = scala.Predef.<:<[A, B]

  def identity[A](a: A): A = a
  def println(x: Any) = scala.Console.println(x)

}
