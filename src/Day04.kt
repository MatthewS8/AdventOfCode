import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf{
            var result = findWinningTickets(it)
            if (result > 1)
                result = 2.0.pow(result - 1.0).toInt()
            result
        }
    }

    fun part2(input: List<String>): Int {
        val tickets = List(input.size) { 1 }.toMutableList()
        input.forEachIndexed { index, row ->
            for (j in 1 .. findWinningTickets(row)) {
                tickets[index + j] += tickets[index]
            }
        }
        return tickets.sum()
    }
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
     check(part2(testInput) == 30)

     val input = readInput("Day04")
     println(part1(input))
     println(part2(input))
}

typealias Card = Pair<Int, List<List<Int>>>

fun serializeStringCards(inputString: String): Card? {
    val matchResult = Regex("Card\\s+(\\d+): (.+?)$").find(inputString)

    return matchResult?.let { result ->
        val cardNumber = result.groupValues[1].toInt()
        val setsPortion = result.groupValues[2]

        val setsList = setsPortion.split('|').map { it.trim() }

        val processedSets = setsList.map { set ->
            set.split(' ')
                .filter { it.isNotEmpty() }
                .map {it.trim().takeWhile { it.isDigit() }.toInt() }

        }

        cardNumber to processedSets
    }
}

fun findWinningTickets(input: String): Int {
    val card = serializeStringCards(input)
    val winningNumbers = card?.second?.first()
    val myNumbers = card?.second?.last()

    var winsPerTicket = 0
    myNumbers?.forEach { win ->
        val wins = winningNumbers?.find { it == win }
        if (wins != null) {
            winsPerTicket++
        }
    }

    return winsPerTicket
}