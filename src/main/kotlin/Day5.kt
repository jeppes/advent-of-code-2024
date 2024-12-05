import org.example.assertAndReturn
import org.example.measure
import org.example.readFile

enum class Part {
    Part1, Part2
}

private fun solve(input: String, part: Part): Int {
    val lines = input.lines()

    val sortingRules = lines.takeWhile { it.isNotEmpty() }

    val printedAfter = mutableMapOf<Int, MutableSet<Int>>()

    for (rule in sortingRules) {
        val (left, right) = rule.split("|").map { it.toInt() }

        val printedBeforeSet = printedAfter[left] ?: mutableSetOf()
        printedAfter[left] = printedBeforeSet

        printedBeforeSet.add(right)
    }

    val problems = lines.drop(sortingRules.size + 1)

    return problems.sumOf { problem ->
        val numbers = problem.split(",").map { it.toInt() }

        val sorted = numbers.sortedWith { left, right ->
            val printedAfterLeft = printedAfter[left] ?: emptySet()
            if (printedAfterLeft.contains(right)) -1
            else 0
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
