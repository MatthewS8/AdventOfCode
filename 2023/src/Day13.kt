fun main() {
    fun part1(input: List<String>): Int {
        val blocks = parsedInput(input)
        return blocks.sumOf {
            getBlockResult(it, 0)
        }
    }

    fun part2(input: List<String>): Int {
        val blocks = parsedInput(input)
        return blocks.sumOf {
            getBlockResult(it, 1)
        }
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 405)
    check(part2(testInput) == 400)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))


}

private fun parsedInput(input: List<String>): List<List<String>> {
    val separatedBlocks: MutableList<List<String>> = mutableListOf()
    val block: MutableList<String> = mutableListOf()

    input.forEach { row ->
        if (row.isBlank()) {
            if (block.isNotEmpty()) {
                separatedBlocks.add(block.toList())
                block.clear()
            }
        } else {
            block.add(row)
        }
    }

    if (block.isNotEmpty()) {
        separatedBlocks.add(block.toList())
    }

    return separatedBlocks
}

private fun getBlockResult(block: List<String>, smudgeNumber: Int): Int {
    var verticalSymmetry = 0

    val horizontalSymmetry = findSymmetry(
        block = block,
        outBound = block.size,
        inBound = block[0].length,
        verticalSearch = false,
        smudgeNumber = smudgeNumber
    )
    if (horizontalSymmetry == 0) {
        verticalSymmetry = findSymmetry(
            block = block,
            outBound = block[0].length,
            inBound = block.size,
            verticalSearch = true,
            smudgeNumber = smudgeNumber
        )
    }
    return verticalSymmetry + 100 * horizontalSymmetry
}

private fun findSymmetry(
    block: List<String>, outBound: Int, inBound: Int, verticalSearch: Boolean, smudgeNumber: Int = 0
): Int {
    var i = 0
    var j = i + 1
    var smudgeSeen = 0
    var symmetricCandidate: Int? = null
    while (i >= 0 && j < outBound) {
        var currentIdx = 0
        while (currentIdx < inBound && smudgeSeen <= smudgeNumber) {
            if (verticalSearch) {
                if (block[currentIdx][i] == block[currentIdx][j]) {
                    currentIdx++
                } else {
                    if (smudgeNumber > 0 && smudgeSeen <= smudgeNumber) {
                        currentIdx++
                        smudgeSeen++
                    } else {
                        break
                    }
                }
            } else {
                if (block[i][currentIdx] == block[j][currentIdx]) {
                    currentIdx++
                } else {
                    if (smudgeNumber > 0 && smudgeSeen <= smudgeNumber) {
                        currentIdx++
                        smudgeSeen++
                    } else {
                        break
                    }
                }
            }
        }
        if (currentIdx == inBound) {
            // they are symmetrical
            if (symmetricCandidate == null) symmetricCandidate = i
            if (smudgeNumber > 0 && smudgeSeen < smudgeNumber && (i - 1 < 0 || j + 1 >= outBound)) {
                // found the reflection without the smudge
                // We have to found the smudge first
                i = symmetricCandidate + 1
                j = i + 1
                symmetricCandidate = null
                smudgeSeen = 0
            } else {
                i--
                j++
            }
        } else {
            // not symmetrical
            if (symmetricCandidate != null) {
                i = symmetricCandidate
                symmetricCandidate = null
            }
            smudgeSeen = 0
            i++
            j = i + 1
        }
    }

    if (symmetricCandidate != null && smudgeSeen == smudgeNumber) return (symmetricCandidate + 1)
    return 0
}

