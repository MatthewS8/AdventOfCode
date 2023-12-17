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
            val size = col.length
            var startIndex = 0
            while(startIndex < size) {
                var currIndex = startIndex
                var roundedRocksFound = 0
                while(currIndex < size && col[currIndex] != '#') {
                    if (col[currIndex] == 'O') roundedRocksFound++
                    currIndex++
                }
                for (i in 0..< roundedRocksFound) {
                    sum += size - i - startIndex
                }
                currIndex++
                startIndex = currIndex
            }
        }
        return sum
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