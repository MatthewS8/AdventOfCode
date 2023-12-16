import kotlin.math.max

fun main() {
    fun lightPathFollower(input: List<String>, startPoint: StartingPoint): Int {
        val visited = input.map { s ->
            BooleanArray(s.length) { false }
        }

        val pathToExplore = mutableListOf(startPoint)
        val pathExplored = mutableSetOf<StartingPoint>()

        while (pathToExplore.isNotEmpty()) {
            val element = pathToExplore.first()
            pathToExplore.addAll(lightPath(element, input, visited))
            pathExplored.add(element)
            pathToExplore.removeAll(pathExplored)
        }

        // visualization
        /*visited.forEach { r ->
            r.forEach { v ->
                print("${if (v) '#' else '.'} ")
            }
            println()
        }*/

        return visited.sumOf { r -> r.count { it } }
    }

    fun part1(input: List<String>): Int {
        return lightPathFollower(input, StartingPoint(0, 0, Direction.RIGHT))
    }

    fun part2(input: List<String>): Int {
        val nRows = input.size
        val nCols = input[0].length

        var max = 0
        for (i in 0..<nRows) {
            max = max(max, lightPathFollower(input, StartingPoint(i, 0, Direction.RIGHT)))
            max = max(max, lightPathFollower(input, StartingPoint(i, nCols - 1, Direction.LEFT)))
        }
        for (j in 0..<nCols) {
            max = max(max, lightPathFollower(input, StartingPoint(0, j, Direction.DOWN)))
            max = max(max, lightPathFollower(input, StartingPoint(nRows - 1, j, Direction.UP)))
        }
        return max
    }

    val testInput = readInput("Day16_test")
    check(part1(testInput) == 46)
    check(part2(testInput) == 51)

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}

fun lightPath(startPoint: StartingPoint, input: List<String>, visited: List<BooleanArray>): MutableSet<StartingPoint> {
    var i = startPoint.row
    var j = startPoint.col
    val rowLength = input[0].length
    val colLength = input.size
    var currDirection = startPoint.direction
    val pathToExplore = mutableSetOf<StartingPoint>()
    while (i in 0..<colLength && j in 0..<rowLength) {
        val a = input[i][j]
        when (currDirection) {
            Direction.DOWN -> when (input[i][j]) {
                '.', '|' -> visited[i++][j] = true
                '\\' -> {
                    currDirection = Direction.RIGHT
                    visited[i][j++] = true
                }

                '/' -> {
                    currDirection = Direction.LEFT
                    visited[i][j--] = true
                }

                '-' -> {
                    pathToExplore.add(StartingPoint(i, j + 1, Direction.RIGHT))
                    pathToExplore.add(StartingPoint(i, j - 1, Direction.LEFT))
                    visited[i][j] = true
                    break
                }
            }

            Direction.UP -> when (input[i][j]) {
                '.', '|' -> visited[i--][j] = true
                '\\' -> {
                    currDirection = Direction.LEFT
                    visited[i][j--] = true
                }

                '/' -> {
                    currDirection = Direction.RIGHT
                    visited[i][j++] = true
                }

                '-' -> {
                    pathToExplore.add(StartingPoint(i, j + 1, Direction.RIGHT))
                    pathToExplore.add(StartingPoint(i, j - 1, Direction.LEFT))
                    visited[i][j] = true
                    break
                }
            }

            Direction.RIGHT -> when (input[i][j]) {
                '.', '-' -> visited[i][j++] = true
                '\\' -> {
                    currDirection = Direction.DOWN
                    visited[i++][j] = true
                }

                '/' -> {
                    currDirection = Direction.UP
                    visited[i--][j] = true
                }

                '|' -> {
                    pathToExplore.add(StartingPoint(i + 1, j, Direction.DOWN))
                    pathToExplore.add(StartingPoint(i - 1, j, Direction.UP))
                    visited[i][j] = true
                    break
                }
            }

            Direction.LEFT -> when (input[i][j]) {
                '.', '-' -> visited[i][j--] = true
                '\\' -> {
                    currDirection = Direction.UP
                    visited[i--][j] = true
                }

                '/' -> {
                    currDirection = Direction.DOWN
                    visited[i++][j] = true
                }

                '|' -> {
                    pathToExplore.add(StartingPoint(i + 1, j, Direction.DOWN))
                    pathToExplore.add(StartingPoint(i - 1, j, Direction.UP))
                    currDirection = Direction.UP
                    visited[i][j] = true
                    break
                }
            }

        }
    }

    return pathToExplore
}

data class StartingPoint(val row: Int, val col: Int, val direction: Direction) {
    override fun toString(): String {
        return "[${this.row}, ${this.col}] -> ${this.direction}"
    }
}


