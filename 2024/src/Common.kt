import kotlin.math.pow

typealias Coordinate = Pair<Int, Int>
fun Coordinate.distanceFrom(other: Coordinate): Pair<Int, Int> {
    return (other.first - this.first) to (other.second - this.second)
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT
}

infix fun Long.concatenateTo(other: Long): Long {
    return (this.toString() + other.toString()).toLong()
}

fun generateCustomBaseSequences(n: Int, base: Int): Sequence<String> {
    return sequence {
        for (i in 0 until (base.toDouble().pow(n).toInt())) {
            yield(i.toString(base).padStart(n, '0'))
        }
    }
}