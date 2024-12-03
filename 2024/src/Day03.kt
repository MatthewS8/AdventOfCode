fun main() {
    fun part1(input: List<String>): Int {
        val mulRegex = Regex("""mul\(\d{1,3},\d{1,3}\)""")
        return input.map { row ->
            mulRegex.findAll(row).map { it.value }.toList()
        }.flatten().map {
            val (a, b) = it.drop(4).dropLast(1).split(",").map { it.toInt() }
            a * b
        }.sum()
    }

    fun part2(input: List<String>): Int {
        // FIX: This won't work for multi-line input. Keep track of the last command of a row.
        val mulRegex = Regex("""mul\(\d{1,3},\d{1,3}\)""")
        val cmdRegex = Regex("""do(?:n't)?""")
        val mulList =  input.map { row ->
            mulRegex.findAll(row).map { it.value to it.range }.toList()
        }.flatten()
        val cmdList = input.map { row ->
            cmdRegex.findAll(row).map { it.value to it.range }.toList()
        }.flatten()
        return mulList.map { (mul, mulRange) ->
            val appliedCommand = cmdList.findLast { (_, cmdRange) -> cmdRange.first < mulRange.first }
            if (appliedCommand != null) {
                if (appliedCommand.first == "do") {
                    val (a, b) = mul.drop(4).dropLast(1).split(",").map { it.toInt() }
                    a * b
                } else {
                    0
                }
            } else {
                val (a, b) = mul.drop(4).dropLast(1).split(",").map { it.toInt() }
                a * b
            }
        }.sum()
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 48)

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
