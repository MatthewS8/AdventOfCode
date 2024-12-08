fun main() {
    fun prepareInput(input: List<String>): MutableMap<Char, MutableList<Coordinate>> {
        val antennaMap = mutableMapOf<Char, MutableList<Coordinate>>()
        input.forEachIndexed { rowIdx, str ->
            str.forEachIndexed { colIdx, c ->
                if (c != '.') {
                    val t = antennaMap.getOrDefault(c, mutableListOf())
                    t.add(Coordinate(rowIdx, colIdx))
                    antennaMap[c] = t
                }
            }
        }
        return antennaMap
    }

    fun part1(input: List<String>): Int {
        val antennaMap = prepareInput(input)
        val antinodes = mutableSetOf<Coordinate>()
        for (v in antennaMap.values) {
            v.forEachIndexed { idx, el ->
                v.indices.filter { it != idx }.forEach { i ->
                    val (dx, dy) = el.distanceFrom(v[i])
                    val antinode = (el.first + (2 * dx)) to (el.second + (2 * dy))
                    if (antinode.isIn(input.size, input[0].length)) antinodes.add(antinode)
                }
            }
        }
        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val antennaMap = prepareInput(input)
        val antinodes = mutableSetOf<Coordinate>()
        for (v in antennaMap.values) {
            v.forEachIndexed { idx, el ->
                antinodes.add(el)
                v.indices.filter { it != idx }.forEach { i ->
                    val (dx, dy) = el.distanceFrom(v[i])
                    var repetitions = 2
                    while (true) {
                        val antinode = (el.first + (repetitions * dx)) to (el.second + (repetitions * dy))
                        if (!antinode.isIn(input.size, input[0].length)) break
                        repetitions++
                        antinodes.add(antinode)
                    }
                }
            }
        }
        return antinodes.size
    }

    // Read a large test input from the `src/Day08_test.txt` file:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    // Read the input from the `src/Day08.txt` file.
    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
