import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    fun readInput(name: String) = Path("src/$name.txt").readText().trimEnd().lines()
    fun part1(input: List<String>): Long {
        val operationMap: MutableList<Long.(Long) -> Long> = mutableListOf()
        val accArray: MutableList<Long> = mutableListOf()
        for (op in input.last()) {
            when (op) {
                '+' -> {
                    operationMap.add(Long::plus)
                    accArray.add(0L)
                }
                '*' -> {
                    operationMap.add(Long::times)
                    accArray.add(1L)
                }
                else -> continue
            }
        }

        for (row in input.dropLast(1)) {
            row.trimStart().split("""\s+""".toRegex()).forEachIndexed { index, value ->
                accArray[index] = operationMap[index](accArray[index], value.toLong())
            }

        }
        return accArray.sum()
    }

    fun part2(input: List<String>): Long {
        val operationMap: MutableList<Long.(Long) -> Long> = mutableListOf()
        val accArray: MutableList<Long> = mutableListOf()
        for (op in input.last()) {
            when (op) {
                '+' -> {
                    operationMap.add(Long::plus)
                    accArray.add(0L)
                }
                '*' -> {
                    operationMap.add(Long::times)
                    accArray.add(1L)
                }
                else -> continue
            }
        }

        var operationCounter = 0
        val nCols = input.maxOf {row -> row.length}
        for (col in 0..<nCols) {
            val stringBuilder = StringBuilder()
            var spaceCounter = 0
            for(row in input.dropLast(1).indices) {
                if (col < input[row].length && input[row][col].isDigit()) {
                    stringBuilder.append(input[row][col])
                }
                else {
                    spaceCounter++
                }
            }
            if (spaceCounter == input.size - 1) {
                operationCounter++
            } else {
                val value = stringBuilder.toString().toLong()
                accArray[operationCounter] = operationMap[operationCounter](accArray[operationCounter], value)
            }
        }

        return accArray.sum()
    }
    // Read a large test input from the `src/Day06_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
