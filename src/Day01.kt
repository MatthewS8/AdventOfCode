import java.lang.Integer.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEach {
            sum += getNumberFromString(it)
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach {
            sum += getNumberDigitsFromString(it)
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

fun getNumberFromString(string: String): Int {
    var i = 0
    var j = string.length - 1
    var res = 0
    var foundI = false
    var foundJ = false
    while (!foundI || !foundJ && i <= j) {
        var tmp: Int?
        if (!foundI) {
            tmp = string[i].digitToIntOrNull()
            if (tmp !== null) {
                res += tmp * 10
                foundI = true
            } else {
                i++
            }
        }
        if (!foundJ) {
            tmp = string[j].digitToIntOrNull()
            if (tmp !== null) {
                res += tmp
                foundJ = true
            } else {
                j--
            }
        }
    }
    return res
}
fun getNumberDigitsFromString(string: String): Int {
    val digits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )
    var i = 0
    var j = string.length - 1
    var res = 0
    var foundI = false
    var foundJ = false
    while (!foundI || !foundJ && i <= j) {
        var tmp: Int?
        if (!foundI) {
            tmp = string[i].digitToIntOrNull()
            if (tmp !== null) {
                res += tmp * 10
                foundI = true
            } else {
                val subs = string.substring(i, min( i+5, string.length))
                for( (d, k) in digits) {
                    if (subs.contains(d)) {
                        res += k * 10
                        foundI = true
                        break
                    }
                }
                i++
            }
        }
        if (!foundJ) {
            tmp = string[j].digitToIntOrNull()
            if (tmp !== null) {
                res += tmp
                foundJ = true
            } else {
                val subs = string.substring(max(0, j-4), j + 1)
                for( (d, k) in digits) {
                    if (subs.contains(d)) {
                        res += k
                        foundJ = true
                        break
                    }
                }
                j--
            }
        }
    }
    println("for ${string} res is ${res}")
    return res
}

