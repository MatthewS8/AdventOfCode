fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            "${it.first(Char::isDigit)}${it.last(Char::isDigit)}".toInt()
        }
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEach {
            val myNumber = getNumberDigitsFromString(it)
            sum += myNumber
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
                for ((d, k) in digits) {
                    val subs = string.substring(i, (i + d.length).coerceAtMost(string.length))
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
                for ((d, k) in digits) {
                    val subs = string.substring(j, (j + d.length).coerceAtMost(string.length))
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
    return res
}
