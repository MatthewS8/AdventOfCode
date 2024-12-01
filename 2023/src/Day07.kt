fun main() {
    fun getHandList(input: List<String>, jAsJoker: Boolean = false): List<Pair<PokerHand, Int>> {
        return input.map {
            it.split(" ").zipWithNext()
        }.flatten()
            .map { (hand, bid) ->
                PokerHand(hand, jAsJoker) to bid.toInt()
            }
    }

    fun part1(input: List<String>): Int {
        val myHand = getHandList(input)

        val sortedList = myHand.sortedWith(compareBy { it.first })
        var sum = 0
        sortedList.mapIndexed { index, (_, bid) ->
            sum += bid * (index + 1)
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val myHand = getHandList(input, true)
        val sortedList = myHand.sortedWith(compareBy { it.first })
        var sum = 0
        sortedList.mapIndexed { index, (_, bid) ->
            sum += bid * (index + 1)
        }
        return sum
    }

    val testInput = readInput("Day07_test")
     check(part1(testInput).also(::println) == 6440)
    check(part2(testInput).also(::println) == 5905)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

private data class PokerCard(val symbol: Char, val jokerPresent: Boolean = false) {
    val value: Int
        get() = when (symbol) {
            'A' -> 13
            'K' -> 12
            'Q' -> 11
            'J' -> if (!jokerPresent) 10 else 0
            'T' -> 9
            '9' -> 8
            '8' -> 7
            '7' -> 6
            '6' -> 5
            '5' -> 4
            '4' -> 3
            '3' -> 2
            '2' -> 1
            else -> throw IllegalArgumentException()
        }
}

private data class PokerHand(val hand: String, val jAsJoker: Boolean = false) : Comparable<PokerHand> {
    val value: Int
        get() = if (hand.length == 5) {
            val handValuated = if (jAsJoker) jollyValuated else hand
            if (handValuated.isFiveOfAKind()) 19
            else if (handValuated.isFourOfAKind()) 18
            else if (handValuated.isFullHouse()) 17
            else if (handValuated.isThreeOfAKind()) 16
            else if (handValuated.isDoublePair()) 15
            else if (handValuated.isPair()) 14
            else if (handValuated.isHighCard()) PokerCard(handValuated.first(), jAsJoker).value
            else 0
        } else {
            throw IllegalArgumentException()
        }

    val jollyValuated: String
        get() =
            hand.replace('J', hand.getMostValuableCard(), true)


    fun String.getMostValuableCard(): Char {
        val cards = filter { it != 'J' }.groupingBy { it }.eachCount()
        if (cards.isNotEmpty()) {
            val mostOccCard = cards.maxBy { it.value }
            val pokerCards = cards.filter { it.value == mostOccCard.value }.map {
                PokerCard(it.key, true)
            }
            return pokerCards.maxBy { it.value }.symbol
        }
        return 'A'
    }

    override fun compareTo(other: PokerHand): Int = when {
        value != other.value -> value - other.value
        else -> {
            val comp = hand.mapIndexed { index, c ->
                val aCard = PokerCard(c, jAsJoker).value
                val bCard = PokerCard(other.hand[index], other.jAsJoker).value
                if (aCard != bCard)
                    aCard - bCard
                else
                    0
            }
            comp.first { it != 0 }
        }
    }

    override fun toString(): String {
        return hand
    }
}

private fun String.isFiveOfAKind(): Boolean {
    return groupingBy { it }.eachCount().count { (_, occ) -> occ == 5 } == 1
}

private fun String.isFourOfAKind(): Boolean {
    return groupingBy { it }.eachCount().count { (_, occ) -> occ == 4 } == 1
}

private fun String.isFullHouse(): Boolean {
    val occurrencesMap = groupingBy { it }.eachCount()
    return occurrencesMap.size == 2 && occurrencesMap.count { (_, occ) -> occ == 3 } == 1
}

private fun String.isThreeOfAKind(): Boolean {
    val firstCard = first()
    val secondSymbol = firstOrNull { it != firstCard }
    val thirdSymbol = firstOrNull { it != firstCard && it != secondSymbol }
    val numberOfFirst = count { it == firstCard }
    val numberOfSecond = count { it == secondSymbol }
    val numberOfThird = count { it == thirdSymbol }
    val isTris = when (numberOfFirst) {
        3 -> numberOfSecond + numberOfThird == 2
        2 -> numberOfSecond == 3 && numberOfThird == 0 || numberOfThird == 3 && numberOfSecond == 0
        1 -> numberOfSecond == 3 && numberOfThird == 1 || numberOfThird == 3 && numberOfSecond == 1
        else -> false
    }
    return isTris
}

private fun String.isDoublePair(): Boolean {
    val occurrencesMap = groupingBy { it }.eachCount().filter { (_, occ) -> occ >= 2 }
    return occurrencesMap.size > 1 && occurrencesMap.count { (_, occ) -> occ == 2 } - occurrencesMap.size == 0
}

private fun String.isPair(): Boolean {
    val occMap = groupingBy { it }.eachCount()
    return occMap.count { (_, appearances) -> appearances > 2 } == 0 &&
            occMap.count { (_, appearances) -> appearances == 2 } == 1
}

private fun String.isHighCard(): Boolean {
    return groupingBy { it }.eachCount().count { (_, occ) -> occ == 1 } == 5
}