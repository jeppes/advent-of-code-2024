package org.example

private fun isSafe(integers: List<Int>): Boolean {
    val diffs = integers.zip(integers.drop(1)).map { (x, y) -> x - y }

    return diffs.all { it in 1..3 } ||
            diffs.all { it < 0 && it > -4 }
}

private fun part1(lines: List<String>): Int {
    return lines.count { line ->
        val integers = line.split(" ")
            .map { it.toInt() }

        isSafe(integers)
    }
}

private fun part2(lines: List<String>): Int {
    return lines.count { line ->
        val allIntegers = line.split(" ")
            .map { it.toInt() }

        for (ignoreIndex in allIntegers.indices) {
            val integers = allIntegers.subList(0, ignoreIndex) + allIntegers.subList(ignoreIndex + 1, allIntegers.size)

            if (isSafe(integers)) {
                return@count true
            }
        }

        return@count false
    }
}


fun main() {
    val input = readFile("/2_1.txt").lines()

    require(measure("Day 1 part 1") { part1(input) } == 463)
    require(measure("Day 1 part 2") { part2(input) } == 514)
}
