import kotlin.test.assertEquals

object Day03 {
    private fun createArea(input: List<Claim>): Array<IntArray> {
        val area = Array(1000) { IntArray(1000) { 0 } }

        input.forEach {
            for (i in it.left until it.left+it.width) {
                for (j in it.top until it.top+it.height) {
                    area[i][j]++
                }
            }
        }

        return area
    }

    fun part1(input: List<Claim>): Int {
        val area = createArea(input)

        return area.fold(0) { acc, ints -> acc + ints.count { it > 1 } }
    }

    fun part2(input: List<Claim>): Int {
        val area = createArea(input)

        outer@ for (it in input) {
            for (i in it.left until it.left + it.width) {
                for (j in it.top until it.top + it.height) {
                    if (area[i][j] > 1) continue@outer
                }
            }
            return it.id
        }

        return -1
    }
}

data class Claim(
    val id: Int,
    val left: Int,
    val top: Int,
    val width: Int,
    val height: Int
) {

    companion object {
        private val REGEX = Regex("\\d+")

        fun fromString(s: String): Claim {
            val (id, left, top, width, height) = REGEX.findAll(s).map { it.value.toInt() }.take(5).toList()

            return Claim(id, left, top, width, height)
        }
    }
}

fun main(args: Array<String>) {
    val input = getInput(3).readLines().map { Claim.fromString(it) }

    assertEquals(111266, Day03.part1(input))
    assertEquals(266, Day03.part2(input))
}
