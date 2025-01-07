fun main() {
    fun part1(inputL: List<String>): Long {
        val input = inputL.first().map { it.toString().toInt() }
        var i = 0
        var j = if (input.size % 2 == 0) input.size else input.size - 1
        j += 2
        var carry = 0
        val disk = mutableListOf<Long>()
        while (j > i) {
            for (z in 0..<input[i]) {
                disk.add((i / 2).toLong())
            }
            i++
            var freeSpace = input[i]
            i++

            do {
                if (carry == 0) j -= 2
                carry = if (carry == 0) input[j] else carry
                while (freeSpace > 0 && carry > 0) {
                    disk.add((j / 2).toLong())
                    freeSpace--
                    carry--
                }
            } while (freeSpace > 0)
        }

        while (carry > 0) {
            disk.add((j / 2).toLong())
            carry--
        }

        var sum = 0L
        disk.forEachIndexed { index, i ->
            sum += i * index
        }

        return sum
    }

    fun part2(inputL: List<String>): Long {
        val input = inputL.first()
        val expandedDisk = mutableListOf<Int>()
        input.forEachIndexed { idx, c ->
            val addVal = if (idx % 2 == 0) idx / 2 else -1
            val count = c.digitToInt()
            for (i in 0 until count) {
                expandedDisk.add(addVal)
            }
        }

        var i: Int
        var j = expandedDisk.size - 1
        while (j > 0) {
            while (expandedDisk[j] == -1) {
                j--
            }

            var chunk = 0

            while (j - chunk > 0 && expandedDisk[j - chunk] == expandedDisk[j]) {
                chunk++
            }

            val bound = j - chunk + 1
            // Let's find the first empty spot that fits
            var freeSpaceCount = 0
            var waterMark = 0
            while (waterMark < bound && freeSpaceCount < chunk) {
                when {
                    waterMark + freeSpaceCount < bound && expandedDisk[waterMark + freeSpaceCount] == -1 -> freeSpaceCount++
                    waterMark + freeSpaceCount < bound && freeSpaceCount > 0 && expandedDisk[waterMark + freeSpaceCount] != -1 -> {
                        waterMark += freeSpaceCount
                        freeSpaceCount = 0
                    }

                    else -> waterMark++
                }
            }
            if (waterMark != bound) {
                i = waterMark
                val currentValue = expandedDisk[j]
                for (z in 0 until chunk) {
                    expandedDisk[i + z] = currentValue
                    expandedDisk[j - z] = -1
                }
                i += chunk
            }
            j -= chunk
        }

        return expandedDisk.foldRightIndexed(0L) { index, el, acc ->
            if (el == -1) acc else acc + (index * el)
        }
    }

    // Read a large test input from the `src/Day09_test.txt` file:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    // Read the input from the `src/Day09.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()

}