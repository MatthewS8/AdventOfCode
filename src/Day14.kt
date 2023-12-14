fun main() {
    fun part1(input: List<String>): Int {
        val map: MutableMap<Int, MutableList<Pair<Int, Char>>> = mutableMapOf()
         input.forEachIndexed { rowIndex, row ->
            val elements = row.mapIndexed { colIndex, char ->
                char to colIndex
            }
            elements.forEach {(char, colIndex) ->
                val list = map[colIndex]
                if (list != null) {
                    list.add(rowIndex to char)
                } else {
                    map[colIndex] = mutableListOf(rowIndex to char)
                }
            }
        }

        val colsStrings: MutableList<String> = mutableListOf()
        for (j in input[0].indices){
            var col = ""
            for(i in input.indices) {
                col += input[i][j]
            }
            colsStrings.add(j, col)
        }

        var sum = 0
        colsStrings.forEach {col ->
            val matchResult = col.split( '#')
            var countedSoFar = col.indexOfFirst { it != '#' }
            matchResult.forEachIndexed { i, str ->
                if (str.isNotEmpty()) {
                    val nOfOs = str.count { it == 'O' }
                    for (ind in 0..<nOfOs)
                        sum += col.length - ind + countedSoFar

                    countedSoFar += str.length
                } else {
                    countedSoFar ++
                }
            }
        }

        /*map.forEach {
            val tmp = it.value.groupBy { it.second }
            val rr = tmp['O']
            val csr = tmp['#']
            val sand = tmp['.']?.toMutableList()
            var sum = 0
            sum = rr?.sumOf { (colIndex, _) ->
                var firstAvailableSpot = sand?.firstOrNull { (s, _) -> s < colIndex }
                if (firstAvailableSpot != null) {
                    val barrier = csr?.firstOrNull {(cubeRocksIndex, _) ->
                        cubeRocksIndex < colIndex && cubeRocksIndex > firstAvailableSpot.first
                    }
                    if (barrier != null) {

                    }
                }
            }
        }*/

        return sum.also(::println)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 136)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}