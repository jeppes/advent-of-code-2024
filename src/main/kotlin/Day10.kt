import org.example.*


private fun part1(grid: Grid<Int>): Int {
    fun reachable9s(
        point: Point,
        grid: Grid<Int>
    ): Set<Point> {
        val current = grid[point]
        if (current == 9) {
            return setOf(point)
        }

        return listOf(Direction.Up, Direction.Right, Direction.Down, Direction.Left)
            .asSequence()
            .map { direction -> point.go(direction) }
            .filter { next -> grid.getOrNull(next) == current + 1 }
            .map { next -> reachable9s(point = next, grid = grid) }
            .flatten()
            .toSet()
    }

    val startingPoints = grid.filter { (_, value) -> value == 0 }

    return startingPoints.sumOf { (point, _) ->
        reachable9s(point = point, grid = grid).count()
    }
}

private fun part2(grid: Grid<Int>): Int {
    fun sumOfPathsTo9(
        point: Point,
        grid: Grid<Int>
    ): Int {
        val current = grid[point]
        if (current == 9) {
            return 1
        }

        return listOf(Direction.Up, Direction.Right, Direction.Down, Direction.Left)
            .asSequence()
            .map { direction -> point.go(direction) }
            .filter { next -> grid.getOrNull(next) == current + 1 }
            .sumOf { next -> sumOfPathsTo9(point = next, grid = grid) }
    }

    val startingPoints = grid.filter { (_, value) -> value == 0 }

    return startingPoints.sumOf { (point, _) ->
        sumOfPathsTo9(point = point, grid = grid)
    }
}

private fun parseGrid(input: String): Grid<Int> {
    return Grid(input.lines().map {
        it.split("")
            .filter { c -> c.isNotBlank() }.map { c -> c.toInt() }
    })
}

fun main() {
    val example1 = """
        0123
        1234
        8765
        9876
    """.trimIndent()

    assertAndReturn(part1(parseGrid(example1)), 1)

    val example2 = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent()
    assertAndReturn(part1(parseGrid(example2)), 36)

    assertAndReturn(
        part1(parseGrid(readFile("/10_1.txt"))),
        517
    )

    assertAndReturn(part2(parseGrid(example2)), 81)
    assertAndReturn(
        part2(parseGrid(readFile("/10_1.txt"))),
        1116
    )
}
