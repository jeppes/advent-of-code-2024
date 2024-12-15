import org.example.assertAndReturn
import org.example.readFile
import kotlin.math.floor
import kotlin.math.log
import kotlin.math.pow

private fun solve(numbers: List<Long>, iterations: Int): Long {
    val cache: MutableMap<Pair<Long, Int>, Long> = mutableMapOf()

    fun solvePair(number: Long, iterations: Int): Long {
        val cached = cache[Pair(number, iterations)]
        if (cached != null) {
            return cached
        }

        val result = run {
            if (iterations == 0) {
                return@run 1
            }

            if (number == 0L) {
                return@run solvePair(1, iterations - 1)
            }

            val numberOfDigits = floor(log(number.toFloat(), base = 10.0f) + 1.0f).toInt()
            if (numberOfDigits % 2 == 0) {
                val powed = (10.0.pow(numberOfDigits / 2)).toLong()
                return@run solvePair(number / powed, iterations - 1) +
                        solvePair(number.rem(powed), iterations - 1)
            } else {
                return@run solvePair(number * 2024L, iterations - 1)
            }
        }

        cache[Pair(number, iterations)] = result
        return result
    }


    var sum = 0L
    for (n in numbers) {
        sum += solvePair(n, iterations = iterations)
    }

    return sum
}


fun main() {
    val numbers = readFile("/11_1.txt").split(" ")
        .filter { it.isNotBlank() }
        .map { it.toLong() }

    assertAndReturn(solve(listOf(125, 17), iterations = 25), 55312)
    assertAndReturn(solve(numbers, iterations = 25), 184927)
    assertAndReturn(solve(numbers, iterations = 75), 220357186726677)
}