package backend.utils

/**
 * Created by d3ft0uch.
 */
object RandomUtils {

  val rnd = new scala.util.Random

  def getRandomLong = rnd.nextLong

  def getRandomSublist[T](lst: List[T], minSize: Int, maxSize: Int) = {
    val range = minSize to maxSize
    rnd.shuffle(lst).take(range(rnd.nextInt(range.length)))
  }
}
