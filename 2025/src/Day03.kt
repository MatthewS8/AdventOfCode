fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { bank ->
            var firstDigit = bank.first().digitToInt() to 0
            for (i in 1..<bank.length - 1) {
                if (bank[i].digitToInt() > firstDigit.first)
                    firstDigit = bank[i].digitToInt() to i
            }

            var secondDigit = bank.last().digitToInt() to bank.length - 1
            for (j in firstDigit.second + 1..<bank.length) {
                if (bank[j].digitToInt() > secondDigit.first)
                    secondDigit = bank[j].digitToInt() to j
            }
            println("$firstDigit -- $secondDigit")
            firstDigit.first * 10 + secondDigit.first
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357)

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    part1(input).println()
//    part2(input).println()
}
