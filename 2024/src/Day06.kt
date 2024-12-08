internal fun Coordinate.isIn(boundRow: Int, boundCol: Int): Boolean {
    val (row, col) = this
    return row in 0 until boundRow && col in 0 until boundCol
}


private fun Direction.turnRight(): Direction {
    return when (this) {
        Direction.UP -> Direction.RIGHT
        Direction.RIGHT -> Direction.DOWN
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
    }
}

private fun Direction.getNextStep(c: Coordinate): Coordinate {
    val (row, col) = c
    return when (this) {
        Direction.UP -> row - 1 to col
        Direction.RIGHT -> row to col + 1
        Direction.DOWN -> row + 1 to col
        Direction.LEFT -> row to col - 1
    }
}

private fun pathWalker(input: List<String>, obstacles: Set<Coordinate>, startingPoint: Coordinate): HashSet<Coordinate> {
    var currentPoint = startingPoint
    var currentDirection = Direction.UP
    val visited = hashSetOf<Coordinate>()
    while (currentPoint.isIn(input.size, input[0].length)) {
        val nextPoint = currentDirection.getNextStep(currentPoint)
        if (nextPoint in obstacles) {
            // change direction, the guard would hit an obstacle
            currentDirection = currentDirection.turnRight()
        } else {
            currentPoint = nextPoint
            if (currentPoint.isIn(input.size, input[0].length))
                visited.add(currentPoint)
        }
    }
    return visited
}

private fun detectLoopBF(input: List<String>, obstacles: Set<Coordinate>, startingPoint: Coordinate): Boolean {
    var currentPoint = startingPoint
    var currentDirection = Direction.UP
    val visited = HashSet<Pair<Coordinate, Direction>>()
    var inLoop = false
    while (currentPoint.isIn(input.size, input[0].length) && !inLoop) {
        val nextPoint = currentDirection.getNextStep(currentPoint)
        if (nextPoint in obstacles) {
            // change direction, the guard would hit an obstacle
            currentDirection = currentDirection.turnRight()
        } else {
            currentPoint = nextPoint
            inLoop = !visited.add(currentPoint to currentDirection)
        }
    }
    return inLoop
}

fun main() {
    fun prepareInput(input: List<String>): Pair<Coordinate, Set<Coordinate>> {
        var startingPoint: Coordinate? = null
        val obstacles = input.mapIndexed { row, str ->
            str.mapIndexed { col, char ->
                when (char) {
                    '#' -> row to col
                    '^' -> {
                        startingPoint = row to col
                        -1 to -1
                    }

                    else -> -1 to -1
                }
            }.filter { it.first != -1 }
        }.flatten().toSet()
        return startingPoint!! to obstacles
    }

    fun part1(input: List<String>): Int {
        val (startingPoint, getObstacles) = prepareInput(input)
        val visited = pathWalker(input, getObstacles, startingPoint)
        return visited.size
    }

    fun part2(input: List<String>): Int {
        val (startingPoint, obstacles) = prepareInput(input)
        val visited = pathWalker(input, obstacles, startingPoint)
        return visited.count { (row, col) ->
            if (startingPoint == (row to col)) {
                return@count false
            }
            val loopObstacles = obstacles.toMutableSet()
            loopObstacles.add(row to col)
            detectLoopBF(input, loopObstacles, startingPoint)
        }
    }

    // Read a large test input from the `src/Day06_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
