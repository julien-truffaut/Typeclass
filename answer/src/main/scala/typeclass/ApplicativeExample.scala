package typeclass


object ApplicativeExample {
  import typeclass.data.{Validation, NonEmptyList}
  import typeclass.data.Validation.{successNel, failureNel}
  import typeclass.syntax.applicative._

  case class Person(name: String, age: Int)

  def positive(n: Int): Validation[NonEmptyList[String], Int] =
  if(n <= 0) failureNel(s"$n is not positive") else successNel(n)

  def capitalise(s: String): Validation[NonEmptyList[String], String] =
  s.headOption.fold(successNel[String, String](s))(c =>
    if(c == c.toUpper) successNel(s)
    else failureNel(s"$s does not start with an upper case")
  )

  def person(name: String, age: Int): Validation[NonEmptyList[String], Person] =
    Applicative[Validation[NonEmptyList[String], ?]].map2(
      capitalise(name), positive(age)
    )(Person(_, _))

  def person2(name: String, age: Int): Validation[NonEmptyList[String], Person] =
    capitalise(name).map2(positive(age))(Person(_, _))
}