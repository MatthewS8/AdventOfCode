import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            countValidGames(it)
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { countPowerOfCubes(it) }
    }

    val testInput = readInput("Day02_test")
    println(part1(testInput))
    check(part1(testInput) == 8)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))

}

fun serializeString(inputString: String): Pair<Int, List<List<Pair<Int, String>>>>? {
    val matchResult = Regex("Game (\\d+): (.+?)$").find(inputString)

    return matchResult?.let { result ->
        val gameNumber = result.groupValues[1].toInt()
        val setsPortion = result.groupValues[2]

        val setsList = setsPortion.split(';').map { it.trim() }

        val processedSets = setsList.map { set ->
            set.split(',').map { pair ->
                val (number, str) = pair.trim().split(" ", limit = 2)
                number.toInt() to str
            }
        }

        gameNumber to processedSets
    }
}

fun countValidGames(game: String): Int {
    val serialized = serializeString(game)
    if (serialized != null) {
        val (gameNumber, processedSets) = serialized
        var gameValid = true
        processedSets.forEachIndexed { index, setList ->
            setList.forEach { (number, str) ->
                gameValid = gameValid && !when (str) {
                    "green" -> number > 13
                    "red" -> number > 12
                    "blue" -> number > 14
                    else -> false
                }
            }
        }
        if (gameValid) return gameNumber
    }
    return 0
}

fun countPowerOfCubes(game: String): Int {
    val serialized = serializeString(game)
    if (serialized != null) {
        val (gameNumber, processedSets) = serialized
        var maxGreen = 0
        var maxRed = 0
        var maxBlue = 0
        processedSets.forEachIndexed { index, setList ->
            setList.forEach { (number, str) ->
                when (str) {
                    "green" -> maxGreen = max(maxGreen, number)
                    "red" -> maxRed = max(maxRed, number)
                    "blue" -> maxBlue = max(maxBlue, number)
                }
            }
        }
        return maxGreen * maxRed * maxBlue
    }
    return 0
}