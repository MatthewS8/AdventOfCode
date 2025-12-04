fun main() {
    fun prepareInput(input: List<String>): MutableSet<Pair<Int, Int>> {
        val set = mutableSetOf<Pair<Int, Int>>()

        input.forEachIndexed { i, row ->
            row.forEachIndexed { j, c ->
                if (c == '@')
                    set.add(i to j)
            }
        }
        return set
    }

    fun hasRoll(rollSet: Set<Pair<Int, Int>>, row: Int, col: Int, rowSize: Int, colSize: Int) =
        row in 0..<rowSize && col in 0..<colSize && rollSet.contains(row to col)

    fun getNeighboursIndexes(row: Int, col: Int): List<Pair<Int, Int>> = listOf(
        row - 1 to col - 1,
        row - 1 to col,
        row - 1 to col + 1,
        row to col - 1,
        row to col + 1,
        row + 1 to col - 1,
        row + 1 to col,
        row + 1 to col + 1,
    )

    fun part1(input: List<String>): Int {
        val rollSet = prepareInput(input)
        val rowSize = input.size
        val colSize = input.first().length
        return rollSet.count { (row, col) ->
            val neighbours = getNeighboursIndexes(row, col)
            neighbours.count { (row, col) -> hasRoll(rollSet, row, col, rowSize, colSize) } < 4
        }
    }

    fun part2(input: List<String>): Int {
        val rollSet = prepareInput(input)
        val rowSize = input.size
        val colSize = input.first().length
        var sum = 0
        do {
            var counted = 0
            rollSet.removeAll { (row, col) ->
                val neighbours = getNeighboursIndexes(row, col)
                neighbours.count { (row, col) -> hasRoll(rollSet, row, col, rowSize, colSize) }
                    .let { count -> (count < 4).also { if (it) counted++ }
                }
            }
            sum += counted
        } while (counted != 0)
        return sum
    }

    // Read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
