fun main() {
    fun part1(input: List<String>): Int {
        val map: MutableMap<Int, Pair<Int, Char>> = mutableMapOf()
        input.mapIndexed { rowIndex, row ->
            val elements = row.mapIndexed { colIndex, char ->
                char to colIndex
            }
            elements.forEach {
                map[it.second] = rowIndex to it.first
            }

        }
        map.forEach { colIndex, u ->
            // TODO: continue from here 
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("DayXX")
    println(part1(input))
    println(part2(input))
}