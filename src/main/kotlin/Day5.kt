import org.example.assertAndReturn
import org.example.measure
import org.example.readFile

enum class Part {
    Part1, Part2
}

private fun solve(input: String, part: Part): Int {
    val lines = input.lines()

    val sortingRules = lines.takeWhile { it.isNotEmpty() }

    val printedBefore = mutableMapOf<Int, MutableSet<Int>>()

    fun saveXComesBeforeY(x: Int, y: Int) {
        if (x == y) return
        val printedBeforeSet = printedBefore[x] ?: mutableSetOf()
        printedBefore[x] = printedBeforeSet

        printedBeforeSet.add(y)
    }

    for (rule in sortingRules) {
        val (left, right) = rule.split("|").map { it.toInt() }
        saveXComesBeforeY(left, right)
    }


    val problems = lines.drop(sortingRules.size + 1)

    return problems.sumOf { problem ->
        val numbers = problem.split(",").map { it.toInt() }

        val sorted = numbers.sortedWith { left, right ->
            printedBefore[left]?.contains(right)?.let { if (it) -1 else 1 } ?: 0
        }

        when (part) {
            Part.Part1 ->
                if (numbers == sorted) numbers[numbers.size / 2] else 0

            Part.Part2 ->
                if (numbers != sorted) sorted[sorted.size / 2] else 0
        }
    }
}


fun main() {
    val input = readFile("/5_1.txt")

    measure("Day 4 Part 1") { assertAndReturn(solve(input, Part.Part1), 5639) }
    measure("Day 4 Part 2") { assertAndReturn(solve(input, Part.Part2), 5273) }
}
