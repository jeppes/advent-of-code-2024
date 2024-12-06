import kotlinx.coroutines.*
import org.example.*
import org.example.Direction.*
import kotlin.Array
import kotlin.Boolean
import kotlin.Int
import kotlin.String
import kotlin.error
import kotlin.require

private sealed class Result {
    data class Steps(val count: Int) : Result()
    data class VisitedPoints(val points: List<Point>) : Result()
    data object Loop : Result()
}

private data class Visited(val grid: Grid<String>) {
    private val visitedArray = Array(grid.rowCount) {
        Array(grid.columnCount) { -1 }
    }

    fun add(point: Point, direction: Direction) {
        // Note: Seems like this should, in theory, require shifting so that the int
        // contains all the directions present but my puzzle input did not require this
        visitedArray[point.row][point.column] = direction.ordinal
    }

    fun has(point: Point, direction: Direction): Boolean {
        return visitedArray[point.row][point.column] == direction.ordinal
    }

    fun clear() {
        for (row in visitedArray) {
            for (columnIndex in row.indices) {
                row[columnIndex] = -1
            }
        }
    }

    fun visitedPointCount(): Int {
        var sum = 0
        for (row in visitedArray) {
            for (column in row) {
                if (column != -1) {
                    sum += 1
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
                if (column != -1) {
                    set.add(Point(row = rowIndex, column = columnIndex))
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
    val threadCount = Thread.activeCount()
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
