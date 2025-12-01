fun main() {
    fun part1(input: List<String>): Int {
        var position = 50
        return input.count { row ->
            val move = (row.drop(1)).toInt()
            when (row.first()) {
                'L' -> position -= move
                'R' -> position += move
            }
            position %= 100
            position == 0
        }
    }

    fun part2(input: List<String>): Int {
        var position = 50
        return input.sumOf { row ->
            val move = row.drop(1).toInt()
            val amount = when (row.first()) {
                'L' -> (move % 100) * -1
                'R' -> (move % 100)
                else -> 0
            }
            val prevPos = position
            position = (position + amount + 100) % 100
            var count = (move / 100)
            if (position == 0 || (prevPos != 0 && prevPos + amount !in 0..<100)) count++
            count
        }
    }

    // Read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
