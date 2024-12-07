import kotlin.math.pow

fun generateCustomBaseSequences(n: Int, base: Int): Sequence<String> {
    return sequence {
        for (i in 0 until (base.toDouble().pow(n).toInt())) {
            yield(i.toString(base).padStart(n, '0'))
        }
    }
}

infix fun Long.concatenateTo(other: Long): Long {
    return (this.toString() + other.toString()).toLong()
}
fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf { expression ->
            val (res, list) = expression.split(':')
            val target = res.trim().toLong()
            val numbers = list.split(' ').drop(1).map(String::toLong)
            if (target == 0L) return@sumOf 0L
            val sum = (0..<numbers.size - 1).firstOrNull { _ ->
                val binary = generateCustomBaseSequences(numbers.size - 1, 2)
                binary.firstOrNull { seq ->
                    var acc = numbers[0]
                    seq.forEachIndexed { idx, c ->
                        if (c == '0') {
                            acc += numbers[idx + 1]
                        } else {
                            acc *= numbers[idx + 1]
                        }
                    }
                    acc == target
                } != null

            }
            if(sum != null)
                target
            else
                0
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { expression ->
            val (res, list) = expression.split(':')
            val target = res.trim().toLong()
            val numbers = list.split(' ').drop(1).map(String::toLong)
            if (target == 0L) return@sumOf 0L
            val sum = (0..<numbers.size - 1).firstOrNull { _ ->
                val binary = generateCustomBaseSequences(numbers.size - 1, 3)
                binary.firstOrNull { seq ->
                    var acc = numbers[0]
                    seq.forEachIndexed { idx, c ->
                        when (c) {
                            '0' -> acc += numbers[idx + 1]
                            '1' -> acc *= numbers[idx + 1]
                            '2' -> acc = acc concatenateTo numbers[idx + 1]
                        }
                    }
                    acc == target
                } != null

            }
            if(sum != null)
                target
            else
                0
        }
    }

    // Read a large test input from the `src/Day07_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    // Read the input from the `src/Day07.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
