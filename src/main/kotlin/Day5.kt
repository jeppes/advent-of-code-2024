import org.example.assertAndReturn
import org.example.measure
import org.example.readFile

private enum class Part {
    Part1, Part2
}

private fun part1(input: String, part: Part): Int {
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

    measure("Day 5 Part 1") { assertAndReturn(part1(input, Part.Part1), 5639) }
    measure("Day 5 Part 2") { assertAndReturn(part1(input, Part.Part2), 5273) }
}
