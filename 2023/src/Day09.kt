import kotlin.math.pow

fun main() {
    /*
    *    10      13      16      21      30      45    68
    *         3      3       5       9       15     23
    *            0      2        4       6       8
    *               2       2         2       2
    *
    * */
    fun getInputParsed(input: List<String>): Sequence<List<Int>> {
        return input.map {
            it.split(" ").map { n -> n.toInt() }
        }.asSequence()
    }
    fun part1(input: List<String>): Int {
        val sequences = getInputParsed(input)

        return sequences.sumOf { seq ->
            var nextSequence = generateNextSequence(seq)
            val lastDiff = mutableListOf(seq.last())

            while (nextSequence.sum() != 0) {
                lastDiff.add(nextSequence.last())
                nextSequence = generateNextSequence(nextSequence)
            }
            lastDiff.sum()
        }
    }

    fun part2(input: List<String>): Int {
        val sequences = getInputParsed(input)

        return sequences.sumOf { seq ->
            var nextSequence = generateNextSequence(seq)
            val lastDiff = mutableListOf(seq.first())
            var iteration = 1

            while (nextSequence.sum() != 0) {
                lastDiff.add(nextSequence.first() * (-1.0).pow(iteration).toInt())
                nextSequence = generateNextSequence(nextSequence)
                iteration++
            }
            lastDiff.sum()
        }
    }

    val inputTest = readInput("Day09_test")
    check(part1(inputTest) == 114)
    check(part2(inputTest) == 2)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}


fun generateNextSequence(seq: List<Int>): List<Int> {
    return seq.windowed(2, 1, true).map{
        it.last() - it.first()
    }.dropLast(1)
}