fun main() {
    fun part1(input: List<String>): Long {
        val seeds = input
            .first()
            .split(" ")
            .let { list -> list.drop(1) }
            .map { it.trim().toLong() }
        return lowestLocationFinder(input, seeds)
    }

    fun part2(input: List<String>): Long {
        val seeds = input
            .first()
            .split(" ")
            .let { list -> list.drop(1) }
            .map { it.trim().toLong() }
            .chunked(2)
            .map { (rangeStart, length) ->
                (rangeStart until (rangeStart + length))
            }
        return part2LocationFinder(input, seeds)
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 35L)
    check(part2(testInput) == 46L)

    val input = readInput("Day05")
    //println(part1(input))
    println(part2(input))
}

data class Range(val start: Long, val length: Long)

fun lowestLocationFinder(input: List<String>, seeds: List<Long>): Long {
    val it = input.drop(3).listIterator()
    var listToFind = seeds.toMutableList()
    val sources = mutableListOf<LongRange>()
    val destination = mutableListOf<LongRange>()
    fun step() {
        val tmp = listToFind.map { element ->
            val isIn = sources.firstOrNull { range ->
                range.contains(element)
            }
            if (isIn != null) {
                val index = sources.indexOf(isIn)
                destination[index].first + (element - isIn.first)
            } else {
                element
            }
        }

        listToFind.clear()
        sources.clear()
        destination.clear()
        listToFind = tmp.toMutableList()
    }


    while (it.hasNext()) {
        val str = it.next()
        if (str.isEmpty() || !str[0].isDigit()) {
            // block parsed
            step()
        } else {
            val (destRange, sourceRange, length) = str.split(' ').map { it.toLong() }
            destination.add(destRange until (destRange + length))
            sources.add(sourceRange until (sourceRange + length))
        }

    }
    step()
    return listToFind.min()
}

fun part2LocationFinder(input: List<String>, seedsRange: List<LongRange>):Long {

    val min = seedsRange.map {
        val seeds = it.toList()
        lowestLocationFinder(input, seeds).also(::println)
    }
    return min.min()
}
