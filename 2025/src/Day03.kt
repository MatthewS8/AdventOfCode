import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { bank ->
            var firstDigit = bank.first().digitToInt() to 0
            for (i in 1..<bank.length - 1) {
                if (bank[i].digitToInt() > firstDigit.first) firstDigit = bank[i].digitToInt() to i
            }

            var secondDigit = bank.last().digitToInt() to bank.length - 1
            for (j in firstDigit.second + 1..<bank.length) {
                if (bank[j].digitToInt() > secondDigit.first) secondDigit = bank[j].digitToInt() to j
            }
            firstDigit.first * 10 + secondDigit.first
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { bank ->
            val digits: MutableList<Pair<Int, Int>> = MutableList(size = 12) { -1 to 0 }
            for (i in 0..<bank.length - 12) {
                if (bank[i].digitToInt() > digits[0].first) digits[0] = bank[i].digitToInt() to i
            }
            for (digitNumber in 11 downTo 1) {
                for (i in digits[12 - digitNumber - 1].second + 1..bank.length - digitNumber) {
                    if (bank[i].digitToInt() > digits[12 - digitNumber].first) digits[12 - digitNumber] =
                        bank[i].digitToInt() to i
                }
            }
            digits.foldRightIndexed(0L) { index, (curr, _), acc -> acc + 10.0.pow(12 - index - 1).toLong() * curr }
        }
    }

    // Read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357)
    check(part2(testInput) == 3121910778619L)

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
