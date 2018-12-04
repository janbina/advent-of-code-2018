import kotlin.test.assertEquals

object Day04 {
    private fun createSleepStats(input: List<Event>): Map<Int, IntArray> {
        val map = mutableMapOf<Int, IntArray>()

        var currentGuardId = -1
        var beginMinute = -1

        input.forEach {
            when (it.type) {
                Event.Type.BEGIN_SHIFT -> currentGuardId = it.guardId
                Event.Type.FALL_ASLEEP -> beginMinute = it.minute
                Event.Type.WAKE_UP -> {
                    val arr = map.getOrPut(currentGuardId) { IntArray(60) }
                    (beginMinute until it.minute).forEach { i -> arr[i]++ }
                }
            }
        }

        return map
    }

    fun part1(input: List<Event>): Int {
        val sleepStats = createSleepStats(input)

        val max = sleepStats.maxBy { it.value.sum() }!!
        val maxMinute = max.value.withIndex().maxBy { it.value }!!.index

        return max.key * maxMinute
    }

    fun part2(input: List<Event>): Int {
        val sleepStats = createSleepStats(input)

        val max = sleepStats.map {
            it.key to it.value.withIndex().maxBy { x -> x.value }!!
        }.maxBy { it.second.value } ?: return 0

        return max.first * max.second.index
    }
}

data class Event(
    val month: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val guardId: Int,
    val type: Type
) {
    enum class Type {
        BEGIN_SHIFT, FALL_ASLEEP, WAKE_UP
    }

    companion object {
        private val REGEX = Regex("\\d+")

        fun fromString(s: String): Event {
            val (m, d, h, min, guardId) = REGEX.findAll(s)
                .map { it.value.toInt() }
                .plus(-1)
                .drop(1)
                .take(5)
                .toList()

            val type = when {
                s.contains("wakes") -> Type.WAKE_UP
                s.contains("falls") -> Type.FALL_ASLEEP
                else -> Type.BEGIN_SHIFT
            }

            return Event(m, d, h, min, guardId, type)
        }
    }
}


fun main(args: Array<String>) {
    val input = getInput(4)
        .readLines()
        .map { Event.fromString(it) }
        .sortedWith(compareBy({ it.month }, { it.day }, { it.hour }, { it.minute }))

    assertEquals(99911, Day04.part1(input))
    assertEquals(65854, Day04.part2(input))
}
