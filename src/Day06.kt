fun main() {
    fun part1(input: List<String>): Long {
        val times = input[0]
            .dropWhile { !it.isDigit() }
            .trim()
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { t -> Race(t.trim().toLong()) }
        val distances = input[1]
            .dropWhile { !it.isDigit() }
            .trim()
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { it.toLong() }
        val races = times.mapIndexed { index, r ->
            r.distanceToBeat = distances[index]
            r
        }
        var m = 1L
        races.forEach {
            val race = getWinningRaceNumber(it)
            if (race > 0) {
                m *= race
            }
        }
        return m
    }

    fun part2(input: List<String>): Long {
        val time = input[0]
            .dropWhile { !it.isDigit() }
            .trim()
            .split(" ")
            .filter { it.isNotEmpty() }
            .joinToString("")
            .toLong()
        val distance = input[1]
            .dropWhile { !it.isDigit() }
            .trim()
            .split(" ")
            .filter { it.isNotEmpty() }
            .joinToString("")
            .toLong()
        return getWinningRaceNumber(Race(time, distance))
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288L)
    check(part2(testInput) == 71503L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

fun getWinningRaceNumber(race: Race): Long {
    var first = 0L

    for (timeHolding in (1 until race.time)) {
        val c = (race.time - timeHolding) * timeHolding
        if (c > race.distanceToBeat) {
            first = timeHolding
            break
        }
    }

    return race.time - (first * 2) + 1
}

data class Race(val time: Long, var distanceToBeat: Long = 0)

