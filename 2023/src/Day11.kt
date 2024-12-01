import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        return sumDistancesOfGalaxies(input, 2)
    }


    fun part2(input: List<String>, factor: Long): Long {
        return sumDistancesOfGalaxies(input, factor)
    }


    val inputTest = readInput("Day11_test")
    check(part1(inputTest) == 374L)
    check(part2(inputTest, 10L) == 1030L)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input, 1_000_000L))
}

fun sumDistancesOfGalaxies(universe: List<String>, expandedFactor: Long): Long {
    val expandedCols = (0 until universe[0].length).toMutableList()
    val expandedRows = universe.indices.toMutableList()

    val galaxies = mutableListOf<Pair<Int, Int>>()
    universe.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { colIndex, char ->
            if (char != '.') {
                expandedCols.remove(colIndex)
                expandedRows.remove(rowIndex)
                galaxies.add(rowIndex to colIndex)
            }
        }
    }

    var distancesSum = 0L
    for (i in galaxies.indices) {
        for (j in (i + 1 until  galaxies.size)) {
            val d = abs( galaxies[i].first - galaxies[j].first) + abs( galaxies[i].second - galaxies[j].second).toLong()
            val expandedRowsInBetween = expandedRows.count { it >= galaxies[i].first && it <= galaxies[j].first}
            val expandedColsInBetween = expandedCols.count { it >= min(galaxies[i].second, galaxies[j].second) && it <= max(galaxies[i].second, galaxies[j].second)}

//                println ("from (${galaxies[i].first}, ${galaxies[i].second}) to (${galaxies[j].first}, ${galaxies[j].second}) there are $expandedRowsInBetween expanded rows and $expandedColsInBetween expanded cols ")
            distancesSum += d + (expandedRowsInBetween + expandedColsInBetween) * (expandedFactor - 1)
        }
    }
    return distancesSum

}