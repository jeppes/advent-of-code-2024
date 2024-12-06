import kotlinx.coroutines.*
import org.example.*
import org.example.Direction.*

private sealed class Result {
    data class Steps(val count: Int) : Result()
    data class VisitedPoints(val points: List<Point>) : Result()
    data object Loop : Result()
}

private data class Visited(val grid: Grid<String>) {
    private val visitedArray = Array(grid.rowCount) {
        Array(grid.columnCount) { Array(4) { false } }
    }

    fun add(point: Point, direction: Direction) {
        visitedArray[point.row][point.column][direction.ordinal] = true
    }

    fun has(point: Point, direction: Direction): Boolean {
        return visitedArray[point.row][point.column][direction.ordinal]
    }

    fun clear() {
        for (row in visitedArray) {
            for (column in row) {
                for (i in column.indices) {
                    column[i] = false
                }
            }
        }
    }

    fun visitedPointCount(): Int {
        var sum = 0
        for (row in visitedArray) {
            for (column in row) {
                for (entry in column) {
                    if (entry) {
                        sum += 1
                        break
                    }
                }
            }
        }
        return sum
    }

    fun visitedPoints(): List<Point> {
        val set = mutableListOf<Point>()
        for (rowIndex in visitedArray.indices) {
            val row = visitedArray[rowIndex]
            for (columnIndex in row.indices) {
                val column = row[columnIndex]
                for (entry in column) {
                    if (entry) {
                        set.add(Point(row = rowIndex, column = columnIndex))
                        break
                    }
                }
            }
        }
        return set
    }
}


private fun part1(
    grid: Grid<String>, newObstructionPoint: Point? = null,
    startPoint: Point,
    visited: Visited,
    includeAllPoints: Boolean = false
): Result {
    var position = startPoint
    var direction = Up

    while (true) {
        if (visited.has(point = position, direction = direction)) {
            return Result.Loop
        }
        visited.add(point = position, direction = direction)

        var nextPoint = position.go(direction)
        var nextValue =
            if (nextPoint == newObstructionPoint) "#" else grid.getOrNull(nextPoint) ?: break

        while (nextValue == "#") {
            direction = when (direction) {
                Left -> Up
                Right -> Down
                Up -> Right
                Down -> Left
                else -> error("Unexpected direction: $direction")
            }
            nextPoint = position.go(direction)
            nextValue =
                if (nextPoint == newObstructionPoint) "#" else grid.getOrNull(nextPoint) ?: break
        }

        position = position.go(direction)
    }

    if (includeAllPoints) return Result.VisitedPoints(visited.visitedPoints())
    return Result.Steps(count = visited.visitedPointCount())
}

private suspend fun part2(
    grid: Grid<String>,
    startPoint: Point,
    pointsToCheck: List<Point>
): Int = withContext(Dispatchers.Default) {
    val threadCount = 500

    (pointsToCheck.chunked(threadCount)).map { points ->
        async {
            val visited = Visited(grid = grid)

            points.count { point ->
                val result = part1(
                    grid = grid,
                    newObstructionPoint = point,
                    startPoint = startPoint,
                    visited = visited
                ) is Result.Loop
                visited.clear()
                result
            }
        }
    }.awaitAll().sum()
}


fun main() {
    val input = readFile("/6_1.txt")
    val grid = Grid(input.lines().map { it.split("").filter { c -> c.isNotBlank() } })
    val (startPoint) = grid.find { (_, value) -> value == "^" }!!

    val part1Result =
        part1(grid = grid, startPoint = startPoint, visited = Visited(grid = grid), includeAllPoints = true)
    require(part1Result is Result.VisitedPoints)
    val visitedPoints = part1Result.points
    assertAndReturn(visitedPoints.size, 5564)

    measure("Day 6 Part 2") {
        assertAndReturn(runBlocking { part2(grid, startPoint, pointsToCheck = visitedPoints) }, 1976)
    }
}
