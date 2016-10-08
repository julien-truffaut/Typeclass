package typeclass.data

import typeclass.Applicative

import scalaprops.Gen

case class ZipStream[A](value: Stream[A]){
  override def toString: String = value.mkString("ZipStream(", ", ", ")")
}

object ZipStream {
  def apply[A](xs: A*): ZipStream[A] = ZipStream(xs.toStream)

  implicit def gen[A: Gen]: Gen[ZipStream[A]] = Gen[Stream[A]].map(ZipStream(_))

  implicit val zipListApplicative: Applicative[ZipStream] = new Applicative[ZipStream] {
    def pure[A](a: A): ZipStream[A] = ZipStream(Stream.continually(a))
    def ap[A, B](fab: ZipStream[A => B], fa: ZipStream[A]): ZipStream[B] =
      ZipStream(fab.value.zip(fa.value).map{ case (f, a) => f(a)})
  }
}