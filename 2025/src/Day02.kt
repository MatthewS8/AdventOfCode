fun main() {
    fun prepareInput(input: List<String>): List<LongRange> = input.map { row ->
        row.split(',').filter { it.isNotBlank() }.map { range ->
            val (from, to) = range.split('-')
            LongRange(from.toLong(), to.toLong())
        }
    }.flatten()

    fun String.splitAtIndex(index: Int) = take(index) to substring(index)

    fun part1(input: List<String>): Long {
        val ranges = prepareInput(input)
        return ranges.sumOf { range ->
            range.sumOf { id ->
                val current = id.toString()
                val (top, bottom) = current.splitAtIndex(current.length / 2)
                if (top.length == bottom.length && top.compareTo(bottom) == 0) {
                    id
                }
                else 0
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
//    part2(input).println()
}
