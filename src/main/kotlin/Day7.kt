import org.example.assertAndReturn
import org.example.measure
import org.example.readFile

private fun possible(target: Long, numbers: List<Long>): Boolean {
    fun search(numbers: List<Long>, total: Long): Boolean {
        if (numbers.isEmpty()) {
            return total == target
        }

        val first = numbers.first()
        val nextNumbers = numbers.drop(1)
        return search(nextNumbers, first * total)
                || search(nextNumbers, first + total)
    }

    return search(numbers.drop(1), total = numbers.first())
}

private fun part1(input: String): Long {
    return input.lines().sumOf { line ->
        val splitOnColon = line.split(":")
        val target = splitOnColon.first().toLong()
        val numbers = splitOnColon[1]
            .split(" ").filter { it.isNotBlank() }
            .map { it.toLong() }

        if (possible(target, numbers)) target else 0
    }
}

private fun possible2(target: Long, numbers: List<Long>): Boolean {
    fun search(numbers: List<Long>, total: Long?): Boolean {
        if (numbers.isEmpty()) {
            return total == target
        }

        val first = numbers.first()
        val nextNumbers = numbers.drop(1)

        return search(nextNumbers, first * (total ?: 1L))
                || search(nextNumbers, first + (total ?: 0L))
                || search(nextNumbers, "${(total ?: "")}$first".toLong())
    }

    return search(numbers, total = null)
}

private fun part2(input: String): Long {
    return input.lines().sumOf { line ->
        val splitOnColon = line.split(":")
        val target = splitOnColon.first().toLong()
        val numbers = splitOnColon[1]
            .split(" ").filter { it.isNotBlank() }
            .map { it.toLong() }

        if (possible2(target, numbers)) target else 0
    }
}


fun main() {
    val input = readFile("/7_1.txt")
    measure("Day 7 Part 1") { assertAndReturn(part1(input), 4122618559853) }
    measure("Day 7 Part 2") { assertAndReturn(part2(input), 227615740238334) }
}
