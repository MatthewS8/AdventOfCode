fun checkIfPresent(char: Char, expectedLocation: Pair<Int, Int>, inputMatrix: List<List<Char>>): Boolean {
    val (row, col) = expectedLocation
    if (row < 0 || row >= inputMatrix.size || col < 0 || col >= inputMatrix[0].size) {
        return false
    }
    return inputMatrix[row][col] == char
}

fun searchSurroundings(char: Char, neighbours: List<Pair<Int, Int>>, matrix: List<List<Char>>): List<Pair<Int, Int>> {
    return neighbours.filter { checkIfPresent(char, it, matrix) }
}

fun isWholeWordPresent(
    startingPoint: Pair<Int, Int>, direction: Pair<Int, Int>, word: String, input: List<List<Char>>
): Boolean {
    // Continue to find the next letter until checkIfPresent returns false, or the whole word is found.
    var wholeWordFound = true
    for (i in 2 until word.length) {
        if (!checkIfPresent(
                word[i], Pair(startingPoint.first + direction.first * i, startingPoint.second + direction.second * i),
                input
            )
        ) {
            wholeWordFound = false
            break
        }
    }
    return wholeWordFound
}

fun main() {

    fun part1(input: List<String>, word: String): Int {
        if (word.isEmpty()) return 0
        val firstChar = word[0]
        val firstMatch = mutableListOf<Pair<Int, Int>>()
        val inputMatrix = input.mapIndexed { idx, row ->
            row.mapIndexed { j, char ->
                if (char == firstChar) firstMatch.add(Pair(idx, j))
                char
            }
        }
        return firstMatch.sumOf { (row, col) ->
            // I want to find the second letter 'M' that are near the 'X'
            val secondChar = word[1]
            val neighbours: List<Pair<Int, Int>> = listOf(
                Pair(row - 1, col), // U
                Pair(row - 1, col + 1), // UR
                Pair(row, col + 1), // R
                Pair(row + 1, col + 1), // DR
                Pair(row + 1, col), // D
                Pair(row + 1, col - 1), // DL
                Pair(row, col - 1), // L
                Pair(row - 1, col - 1) // UL
            )
            val secondMatches = searchSurroundings(secondChar, neighbours, inputMatrix)
            secondMatches.count { (r, c) ->
                val direction = Pair(r - row, c - col)
                isWholeWordPresent(startingPoint = Pair(row, col), direction, word, inputMatrix)
            }
        }
    }

    fun part2(input: List<String>, word: String): Int {
        if (word.isEmpty()) return 0
        val firstChar = word[0]
        val firstMatch = mutableListOf<Pair<Int, Int>>()
        val inputMatrix = input.mapIndexed { idx, row ->
            row.mapIndexed { j, char ->
                if (char == firstChar) firstMatch.add(Pair(idx, j))
                char
            }
        }
        val backMAS = mutableSetOf<Pair<Int, Int>>()
        val forwardMAS = mutableSetOf<Pair<Int, Int>>()
        firstMatch.forEach { (row, col) ->
            val secondChar = word[1]
            val backNeighbours: List<Pair<Int, Int>> = listOf(
                Pair(row + 1, col + 1), // DR
                Pair(row - 1, col - 1) // UL
            )
            val forwardNeighbours: List<Pair<Int, Int>> = listOf(
                Pair(row - 1, col + 1), // UR
                Pair(row + 1, col - 1), // DL
            )
            val backMatches = searchSurroundings(secondChar, backNeighbours, inputMatrix)
            backMAS.addAll(backMatches.filter { (r, c) ->
                val direction = Pair(r - row, c - col)
                isWholeWordPresent(startingPoint = Pair(row, col), direction, word, inputMatrix)
            })
            val forwardMatches = searchSurroundings(secondChar, forwardNeighbours, inputMatrix)
            forwardMAS.addAll(forwardMatches.filter { (r, c) ->
                val direction = Pair(r - row, c - col)
                isWholeWordPresent(startingPoint = Pair(row, col), direction, word, inputMatrix)
            })
        }
        return backMAS.intersect(forwardMAS).size
    }

    // Test
    val testInput = readInput("Day04_test")
    check(part1(testInput, "XMAS") == 18)
    check(part2(testInput, "MAS") == 9)
    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    part1(input, "XMAS").println()
    part2(input, "MAS").println()
}
