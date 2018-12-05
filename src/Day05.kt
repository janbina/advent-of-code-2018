import java.util.Stack
import kotlin.math.abs
import kotlin.test.assertEquals

object Day05 {

    private val lcUcDiff = 'a' - 'A'

    private fun react(string: String, vararg skip: Char): Int {
        val stack = Stack<Char>()

        string.forEach {
            if (it in skip) {
                // no-op
            } else if (stack.empty().not() && abs(stack.peek() - it) == lcUcDiff) {
                stack.pop()
            } else {
                stack.push(it)
            }
        }

        return stack.size
    }


    fun part1(input: String): Int {
        return react(input)
    }

    fun part2(input: String): Int {
        return ('a'..'z').map { react(input, it, it.toUpperCase()) }.min() ?: -1
    }
}


fun main(args: Array<String>) {
    val input = getInput(5).readText()

    assertEquals(10978, Day05.part1(input))
    assertEquals(4840, Day05.part2(input))
}
