package typeclass.std

import typeclass.{Monad, Monoid, Semigroup}

object option {

  implicit def optionMonoid[A: Semigroup]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def empty: Option[A] = None
    def combine(x: Option[A], y: Option[A]): Option[A] = (x, y) match {
      case (None, None)       => None
      case (Some(a), None)    => Some(a)
      case (None, Some(a))    => Some(a)
      case (Some(a), Some(b)) => Some(Semigroup[A].combine(a, b))
    }
  }

  implicit val optionMonad: Monad[Option] = new Monad[Option] {
    def pure[A](a: A): Option[A] = Option(a)
    def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = fa match {
      case None    => None
      case Some(a) => f(a)
    }
  }

}
