import kotlin.math.abs
import kotlin.math.max

fun isDiffValid (a: Long, b: Long): Boolean {
    return abs(a - b) <= 3 && (a - b) != 0L
}

fun List<Long>.indexOfLastSafe(): Int {
    val isIncreasing = this[1] - this[0] > 0L
    for ( i in 0..size - 2) {
        if (!isDiffValid(this[i + 1], this[i])) {
            return i
        } else {
            if (isIncreasing != this[i + 1] - this[i] > 0L) {
                return i
            }
        }
    }
    return size
}
fun main() {
    fun part1(input: List<String>): Int {
        val isSafeList = input.map { row ->
            val report = row.split(" ").map { it.toLong() }
            report.indexOfLastSafe() == report.size
        }
        return isSafeList.count { it }
    }

    fun part2(input: List<String>): Int {
        val isSafeList = input.map { row ->
            val report = row.split(" ").map { it.toLong() }
            val lastSafeIndex = report.indexOfLastSafe()
            if (lastSafeIndex != report.size) {
                val possibleIndexes = listOf(max(lastSafeIndex - 1, 0), lastSafeIndex, lastSafeIndex + 1)
                for(i in possibleIndexes) {
                    val subList = report.toMutableList()
                    subList.removeAt(i)
                    if (subList.indexOfLastSafe() == subList.size) {
                        return@map true
                    }
                }
                false
            } else {
                true
            }
        }
        return isSafeList.count { it }
    }

    val testInput = readInput("Day02_test")
    check(part2(testInput).also(::println) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
