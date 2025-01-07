import kotlin.math.pow

typealias Coordinate = Pair<Int, Int>

fun Coordinate.isIn(boundRow: Int, boundCol: Int): Boolean {
    val (row, col) = this
    return row in 0 until boundRow && col in 0 until boundCol
}

fun Coordinate.distanceFrom(other: Coordinate): Pair<Int, Int> {
    return (other.first - this.first) to (other.second - this.second)
}

fun Coordinate.getNextInDirectionOrNull(d: Direction, width: Int, height: Int): Coordinate? {
    val c = when (d) {
        Direction.UP -> (first - 1) to second
        Direction.RIGHT -> first to (second + 1)
        Direction.DOWN -> (first + 1) to second
        Direction.LEFT -> first to (second - 1)
    }

    return if(c.isIn(width, height)) c else null
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