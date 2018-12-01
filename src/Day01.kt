import kotlin.test.assertEquals

object Day01 {
    fun part1(input: List<Int>): Int {
        return input.sum()
    }

    fun part2(input: List<Int>): Int {
        val set = mutableSetOf(0)
        var current = 0

        input.asRepeatedSequence()
            .forEach {
                current += it
                if (set.add(current).not()) {
                    return current
                }
            }

        throw IllegalStateException()
    }
}

fun main(args: Array<String>) {
    val input = getInput(1).readLines().map { it.toInt() }

    assertEquals(520, Day01.part1(input))
    assertEquals(394, Day01.part2(input))
}
