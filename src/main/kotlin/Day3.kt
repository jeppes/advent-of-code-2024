package org.example

private fun part1(input: String): Int {
    return Regex("mul\\(\\d+,\\d+\\)").findAll(input).sumOf { match ->
        Regex("\\d+").findAll(match.value).fold(1) { acc, it -> acc * it.value.toInt() }.toInt()
    }
}

private fun part2(input: String): Int {
    val multiplications = Regex("mul\\(\\d+,\\d+\\)").findAll(input).toList()
    val donts = Regex("don't\\(\\)").findAll(input).toList()
    val dos = Regex("do\\(\\)").findAll(input).toList()

    var isOn = true
    var sum = 0
    for (match in (multiplications + donts + dos).sortedBy { it.range.first }) {
        if (match.value == "don't()") {
            isOn = false
        } else if (match.value == "do()") {
            isOn = true
        } else if (isOn) {
            sum += Regex("\\d+").findAll(match.value).fold(1) { acc, it -> acc * it.value.toInt() }.toInt()
        }
    }

    return sum
}

fun main() {
    val input = readFile("/3_1.txt")
    measure("Day 3 Part 1") { assertAndReturn(part1(input), 174960292) }
    measure("Day 3 Part 2") { assertAndReturn(part2(input), 56275602) }
}
