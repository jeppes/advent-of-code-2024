import org.example.*

private fun part1(grid: Grid<String>): Int {
    val antennas = grid.filter { (_, value) -> value != "." }

    val antinodes = mutableSetOf<Point>()

    for ((antennaPoint1, antenna1) in antennas) {
        for ((antennaPoint2, antenna2) in antennas) {
            if (antennaPoint1 == antennaPoint2 || antenna1 != antenna2) {
                continue
            }

            val rowDiff = antennaPoint2.row - antennaPoint1.row
            val columnDiff = antennaPoint2.column - antennaPoint1.column

            val antinodePoint = Point(row = antennaPoint1.row - rowDiff, column = antennaPoint1.column - columnDiff)
            if (grid.getOrNull(antinodePoint) != null) {
                antinodes += antinodePoint
            }
        }
    }


    return antinodes.size
}

private fun part2(grid: Grid<String>): Int {
    val antennas = grid.filter { (_, value) -> value != "." }

    val antinodes = mutableSetOf<Point>()

    for ((antennaPoint1, antenna1) in antennas) {
        for ((antennaPoint2, antenna2) in antennas) {
            if (antennaPoint1 == antennaPoint2 || antenna1 != antenna2) {
                continue
            }

            val rowDiff = antennaPoint2.row - antennaPoint1.row
            val columnDiff = antennaPoint2.column - antennaPoint1.column

            var antinodePoint = Point(row = antennaPoint1.row - rowDiff, column = antennaPoint1.column - columnDiff)
            while (grid.getOrNull(antinodePoint) != null) {
                antinodes += antinodePoint
                antinodePoint = Point(row = antinodePoint.row - rowDiff, column = antinodePoint.column - columnDiff)
            }
        }
    }

    return (antinodes + antennas.map { it.first }).toSet().size
}


fun main() {
    measure("Day 8 Part 2") {
        val grid = Grid(readFile("/8_1.txt").lines().map { it.split("").filter { c -> c.isNotBlank() } })
        assertAndReturn(part1(grid), 256)
    }

    measure("Day 8 Part 2") {
        val grid = Grid(readFile("/8_1.txt").lines().map { it.split("").filter { c -> c.isNotBlank() } })
        assertAndReturn(part2(grid), 1005)
    }
}
