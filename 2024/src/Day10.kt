fun main() {
    fun checkWalk(
        curr: Coordinate, input: List<List<Int>>, valid: MutableList<MutableList<Pair<Boolean, Set<Coordinate>>>>
    ): Set<Coordinate> {
        if (valid[curr.first][curr.second].first) {
            return valid[curr.first][curr.second].second
        }
        if (input[curr.first][curr.second] == 9) {
            return setOf(curr)
        }
        val height = input.size
        val width = input[0].size
        for (direction in Direction.entries) {
            val next = curr.getNextInDirectionOrNull(direction, width, height)
            if (next != null && input[next.first][next.second] == input[curr.first][curr.second] + 1) {
                val res = checkWalk(next, input, valid).toMutableSet()
                res.addAll(valid[curr.first][curr.second].second)
                valid[curr.first][curr.second] = true to res
            }
        }
        return valid[curr.first][curr.second].second
    }

    fun checkWalk2(curr: Coordinate, input: List<List<Int>>, valid: MutableList<MutableList<Pair<Int, Set<Coordinate>>>>): Int {
        if(valid[curr.first][curr.second].first != -1) {
            return valid[curr.first][curr.second].first
        }
        if(input[curr.first][curr.second] == 9) {
            return 1
        }
        val height = input.size
        val width = input[0].size
        var sum = 0
        for (direction in Direction.entries) {
            val next = curr.getNextInDirectionOrNull(direction, width, height)
            if (next != null && input[next.first][next.second] == input[curr.first][curr.second] + 1) {
                valid[next.first][next.second] = checkWalk2(next, input, valid) to valid[next.first][next.second].second
                sum += valid[next.first][next.second].first
            }
        }
        return sum
    }

    fun part1(input: List<String>): Int {
        val valid = MutableList(input.size) { MutableList(input[0].length) { false to emptySet<Coordinate>() } }
        val startingPoints = mutableListOf<Coordinate>()
        val inputInt = input.mapIndexed { row, line ->
            line.mapIndexed { col, h ->
                if (h.toString().toInt() == 0) {
                    startingPoints.add(row to col)
                }
                h.toString().toInt()
            }
        }
        return startingPoints.sumOf {
            checkWalk(it, inputInt, valid).size
        }
    }

    fun part2(input: List<String>): Int {
        val valid = MutableList(input.size) { MutableList(input[0].length) { -1 to emptySet<Coordinate>() } }
        val startingPoints = mutableListOf<Coordinate>()
        val inputInt = input.mapIndexed { row, line ->
            line.mapIndexed { col, h ->
                if (h.toString().toInt() == 0) {
                    startingPoints.add(row to col)
                }
                h.toString().toInt()
            }
        }
        return startingPoints.sumOf {
            checkWalk2(it, inputInt, valid)
        }
    }

    // Read a large test input from the `src/Day10_test.txt` file:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    // Read the input from the `src/Day10.txt` file.
    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}