private fun main() {
    fun part1(input: List<String>): Int {
        return input.first().split(',').sumOf {
            hash(it)
        }
    }

    fun part2(input: List<String>): Int {
        val instructions = input.first().split(',')
        val lensInstr = instructions.map {
            val label = it.takeWhile { c -> c != '-' && c != '=' }
            val operation = if (it.endsWith('-')) Operation.REMOVE else Operation.ADD
            if (operation == Operation.ADD)
                LensInstr(label, operation, it.trimEnd().takeLastWhile { c -> c.isDigit() }.toInt())
            else
                LensInstr(label, operation, 0)

        }
        val hashmap: MutableMap<Int, MutableList<LensInstr>> = mutableMapOf()
        lensInstr.forEach {
            val box = hash(it.label)
            val list = hashmap[box]
            if (list != null) {
                val element = list.find { (lab, _, _) ->
                    lab == it.label
                }
                when (it.operation) {
                    Operation.ADD -> {
                        if (element != null) {
                            element.focalLength = it.focalLength
                            element.operation = it.operation
                        } else {
                            list.add(it)
                        }
                    }

                    Operation.REMOVE -> {
                        if (element != null) {
                            list.remove(element)
                        }
                    }
                }
            } else {
                if (it.operation == Operation.ADD)
                    hashmap[box] = mutableListOf(it)
            }
        }

        var sum = 0
        hashmap.forEach { (box, l) ->
            l.forEachIndexed { pos, len ->
                sum += (box + 1) * (pos + 1) * len.focalLength
            }
        }
        return sum
    }

    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1320)
    check(part2(testInput) == 145)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}

fun hash(instruction: String): Int {
    var currentValue = 0
    instruction.forEach {
        currentValue += it.code
        currentValue *= 17
        currentValue %= 256
    }
    return currentValue
}

private data class LensInstr(val label: String, var operation: Operation, var focalLength: Int)

private enum class Operation {
    REMOVE, ADD
}