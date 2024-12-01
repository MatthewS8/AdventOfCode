fun main() {
    val regex = """(\w+) = \((\w+), (\w+)\)""".toRegex()

    fun part1(input: List<String>): Int {
        val path = input[0].trim()
        val desertMap = mutableMapOf<String, Pair<String, String>>()
        input.drop(2).forEach { row ->
            val matchResult = regex.find(row)
            val (key, left, right) = matchResult!!.destructured
            desertMap[key] = Pair(left, right)
        }

        var steps = 0
        var found = "AAA"
        while (!found.endsWith('Z')) {
            val direction = path[steps % path.length]
            found = when (direction) {
                'L' -> desertMap[found]!!.first
                else -> desertMap[found]!!.second
            }
            steps++
        }

        return steps
    }

    fun part2(input: List<String>): Long {
        val path = input[0].trim()
        val desertMap = mutableMapOf<String, Pair<String, String>>()
        input.drop(2).forEach { row ->
            val matchResult = regex.find(row)
            val (key, left, right) = matchResult!!.destructured
            desertMap[key] = Pair(left, right)
        }

        val multiPath = desertMap.keys.filter { it.endsWith('A') }

        val pathLengths: List<Long> = multiPath.map {
            var found = it
            var steps = 0L
            while (!found.endsWith('Z')) {
                val direction = path[(steps % path.length).toInt()]
                found = when (direction) {
                    'L' -> desertMap[found]!!.first
                    else -> desertMap[found]!!.second
                }
                steps++
            }
            steps
        }
        return pathLengths.lcm()
    }

    val testInput = readInput("Day08_test")
//    check(part1(testInput) == 2)
    check(part2(testInput) == 6L)


    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}


fun List<Long>.lcm(): Long {
    if (isEmpty()) return 0

    return reduce { acc, number ->
        acc.lcm(number)
    }
}

fun Long.lcm(other: Long): Long {
    return this * other / gdc(other)
}

fun Long.gdc(other: Long): Long {
    var num1 = this
    var num2 = other

    while (num2 != 0L) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }

    return num1
}