import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Long {
        val parsedInput = input.map { row ->
            val tmp = row.split(" ", ",")
            SpringConditionRecord(tmp.first(), tmp.drop(1).map { it.toInt() }.toMutableList())
        }
        return parsedInput.sumOf {
            it.countValidArrangements()
        }
    }

    fun part2(input: List<String>): Long {
        val parsedInput = input.map { row ->
            val (record, numberLists) = row.split(" ")
            val numbers = numberLists.split(",").map { it.toInt() }
            var recordUnfolded = record
            val numbersUnfolded = numbers.toMutableList()
            for (i in 1 until 5) {
                recordUnfolded += "?$record"
                numbersUnfolded += numbersUnfolded
            }
            println("ru: $recordUnfolded, ${numbersUnfolded.size} ${numbers.size}")
            SpringConditionRecord(recordUnfolded, numbersUnfolded)
        }

        return parsedInput.sumOf {
            it.countValidArrangements().also(::println)
        }
    }


    val testInput = readInput("Day12_test")
//    check(part1(testInput) == 21L)
    check(part2(testInput) == 525152L)


    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

data class SpringConditionRecord(val springRecord: String, val numRecord: List<Int>)

fun SpringConditionRecord.countValidArrangements(): Long {
    val allSequences = generateSequence(0) { it + 1 }
        .take((2.0.pow(springRecord.count { it == '?' })).toInt())
        .map { Integer.toBinaryString(it) }
        .map { binaryString ->
            binaryString.map {
                if (it == '0') '#' else '.'
            }
                .joinToString("")
                .padStart(springRecord.count { it == '?' }, '#')
        }

    var counter = 0L
    for (seq in allSequences) {
        var subsCounter = 0
        val newSequence = springRecord.map {
            if (it == '?')
                seq[subsCounter++]
            else
                it
        }.joinToString("")

        // check correctness
        val splitList = newSequence.split(".").filter { it.isNotEmpty() }
        if (splitList.size == numRecord.size) {
            val zipped = splitList.zip(numRecord)
            val isValid = zipped.map { (str, qty) ->
                str.count { it == '#' } == qty
            }.all { it }

            if (isValid) counter++
        }
    }

    return counter
}