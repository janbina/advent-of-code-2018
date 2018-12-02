import kotlin.test.assertEquals

object Day02 {
    fun part1(input: List<String>): Int {
        var two = 0
        var three = 0

        input
            .map { str -> str.groupingBy { it }.eachCount() }
            .map { it.values }
            .forEach { counts ->
                if (counts.any { it == 2 }) two++
                if (counts.any { it == 3 }) three++
            }

        return two * three
    }

    fun part2(input: List<String>): String {
        val pair = input.asSequence().uniquePairs()
            .filter { stringDistance(it.first, it.second) == 1 }
            .first()

        return pair.first.zip(pair.second)
            .filter { it.first == it.second }
            .map { it.first }
            .joinToString(separator = "")
    }
}

fun main(args: Array<String>) {
    val input = getInput(2).readLines()

    assertEquals(6000, Day02.part1(input))
    assertEquals("pbykrmjmizwhxlqnasfgtycdv", Day02.part2(input))
}

private fun stringDistance(a: String, b: String): Int {
    return a.zip(b).count { it.first != it.second }
}