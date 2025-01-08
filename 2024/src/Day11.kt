private fun Long.countDigits(): Int {
    var count = 0
    var num = this
    while (num != 0L) {
        num /= 10L
        ++count
    }
    return count
}

fun MutableMap<Long, Long>.addOrIncrement(key: Long, count: Long) {
    val curr = this.getOrDefault(key, 0L)
    this[key] = curr + count
}

fun main() {
    fun part1(input: String): Int {
        var stoneList = input.split(" ").map { it.toLong() }
        val iterations = 25

        for (i in 1..iterations) {
            stoneList = stoneList.map { stone ->
                when {
                    stone == 0L -> listOf(1L)
                    stone.countDigits() % 2 == 0 -> {
                        val half = stone.countDigits() / 2
                        val firstHalf = stone.toString().substring(0, half).toLong()
                        val secondHalf = stone.toString().substring(half).toLong()
                        listOf(firstHalf, secondHalf)
                    }

                    else -> listOf(stone * 2024L)
                }
            }.flatten()

        }

        return stoneList.size
    }

    fun part2(input: String): Long {
        val stoneList = input.split(" ").map { it.toLong() }.groupingBy { it }.eachCount()
        var countMap = mutableMapOf<Long, Long>()
        stoneList.forEach { (stone, count) ->
            countMap[stone] = count.toLong()
        }
        repeat(75) {
            val elementsToAdd = mutableMapOf<Long, Long>()
            countMap.forEach { (stone, count) ->
                when {
                    stone == 0L -> elementsToAdd.addOrIncrement(1L, count)
                    stone.countDigits() % 2 == 0 -> {
                        //split the number in half e.g. 1234 -> 12, 34
                        val half = stone.countDigits() / 2
                        val firstHalf = stone.toString().substring(0, half).toLong()
                        val secondHalf = stone.toString().substring(half).toLong()
                        elementsToAdd.addOrIncrement(firstHalf, count)
                        elementsToAdd.addOrIncrement(secondHalf, count)
                    }

                    else -> elementsToAdd.addOrIncrement(stone * 2024L, count)
                }
            }
            countMap = elementsToAdd
        }

        return countMap.values.sum()
    }


    // Or read a large test input from the `src/Day11_test.txt` file:
    val testInput = readInput("Day11_test")[0]
    check(part1(testInput) == 55312)

    // Read the input from the `src/Day11.txt` file.
    val input = readInput("Day11")[0]
    part1(input).println()
    part2(input).println()
}
