import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()
        input.forEach { row ->
            val (l, r) = row.split(Regex("\\s+"))
                .map(String::toInt)
            leftList.add(l)
            rightList.add(r)
        }
        leftList.sort()
        rightList.sort()
        return leftList.zip(rightList).sumOf { abs(it.second - it.first) }
    }

    fun part2(input: List<String>): Int {
        val leftOccurrences = HashMap<Int, Int>()
        val rightOccurrences = HashMap<Int, Int>()
        input.forEach { row ->
            val (left, right) = row.split(Regex("\\s+")).map(String::toInt)
            leftOccurrences[left] = leftOccurrences.getOrDefault(left, 0) + 1
            rightOccurrences[right] = rightOccurrences.getOrDefault(right, 0) + 1
        }
        return leftOccurrences.map { (key, value) ->
            value * key * rightOccurrences.getOrDefault(key, 0)
        }.sum()
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
