fun main() {
    fun part1(input: List<String>, availableSteps: Int): Int {
        var row = 0
        val nodeReachedAtNSteps = mutableSetOf<StartingPoint>()
        val nodesAtNextSteps = mutableSetOf<StartingPoint>()
        for (l in input) {
            val sIndex = l.indexOf('S')
            if (sIndex != -1) {
                nodesAtNextSteps.add(StartingPoint(row, sIndex))
                break
            }
            row++
        }

        var stepsDone = 0

        val nOfRows = input.size
        val nOfCols = input[0].length

        while (stepsDone <= availableSteps) {
            nodeReachedAtNSteps.clear()
            nodeReachedAtNSteps.addAll(nodesAtNextSteps)
            nodesAtNextSteps.clear()
            nodeReachedAtNSteps.forEach { node ->
                val upNode = StartingPoint(node.row - 1, node.col)
                val rightNode = StartingPoint(node.row, node.col + 1)
                val downNode = StartingPoint(node.row + 1, node.col)
                val leftNode = StartingPoint(node.row, node.col - 1)

                if (!upNode.isAboveUpBorder() && input[upNode.row][upNode.col] != '#') {
                    nodesAtNextSteps.add(upNode)
                }
                if (!rightNode.isOverRightBorder(nOfCols) && input[rightNode.row][rightNode.col] != '#') {
                    nodesAtNextSteps.add(rightNode)
                }
                if (!downNode.isBelowDownBorder(nOfRows) && input[downNode.row][downNode.col] != '#') {
                    nodesAtNextSteps.add(downNode)
                }
                if (!leftNode.isBeforeLeftBorder() && input[leftNode.row][leftNode.col] != '#') {
                    nodesAtNextSteps.add(leftNode)
                }
            }
            stepsDone++
        }
        return nodeReachedAtNSteps.count()
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day21_test")
    check(part1(testInput, 6) == 16)
    check(part2(testInput) == 0)

    val input = readInput("Day21")
    println(part1(input, 64))
    println(part2(input))
}

fun StartingPoint.isAboveUpBorder(): Boolean {
    return row < 0
}

fun StartingPoint.isBelowDownBorder(nOfRows: Int): Boolean {
    return row >= nOfRows
}

fun StartingPoint.isBeforeLeftBorder(): Boolean {
    return col < 0
}

fun StartingPoint.isOverRightBorder(nOfCols: Int): Boolean {
    return col >= nOfCols
}