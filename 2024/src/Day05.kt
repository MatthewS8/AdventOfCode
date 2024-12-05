fun main() {
    fun parseRules(input: List<String>): HashMap<Int, MutableSet<Int>> {
        val rules: HashMap<Int, MutableSet<Int>> = hashMapOf()
        input.takeWhile { it.isNotEmpty() }.forEach {
            val (first, second) = it.split('|').map(String::toInt)
            rules.computeIfAbsent(first) { mutableSetOf() }.add(second)
        }
        return rules
    }

    fun validateInstructions(instructions: List<Int>, rules: HashMap<Int, MutableSet<Int>>): Boolean {
        val reversed = instructions.reversed()
        for ((index, el) in reversed.withIndex()) {
            rules[el]?.let { find ->
                val subset = reversed.subList(index + 1, reversed.size).toSet()
                if (find.intersect(subset).isNotEmpty()) return false
            }
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val rules = parseRules(input)
        val instructionList = input.dropWhile { it.isNotEmpty() }.drop(1)
        return instructionList.sumOf { row ->
            val instructions = row.split(',').map(String::toInt)
            if (validateInstructions(instructions, rules)) {
                instructions[(instructions.size / 2.0).toInt()]
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>): Int {
        val rules: HashMap<Int, MutableSet<Int>> = parseRules(input)
        val instructionList = input.dropWhile { it.isNotEmpty() }.drop(1)
        return instructionList.sumOf { row ->
            val instructions = row.split(',').map(String::toInt)
            if (!validateInstructions(instructions, rules)) {
                val sorted = instructions.sortedWith { a, b ->
                    if (rules[a]?.contains(b) == true) -1 else 1
                }
                sorted[(sorted.size / 2.0).toInt()]
            } else {
                0
            }
        }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
