package org.example

private fun part1(grid: Grid<Char>): Int {
    val xmas = listOf('X', 'M', 'A', 'S')
    val allDirections = listOf(
        Direction.Up, Direction.Down, Direction.Left, Direction.Right,
        Direction.UpLeft, Direction.UpRight, Direction.DownLeft, Direction.DownRight
    )

    return grid.sumOf { (point, char) ->
        if (char == 'X') {
            allDirections
                .map { direction -> grid.walkInDirection(startAt = point, steps = 3, direction = direction) }
                .count { xmas == it }
        } else {
            0
        }
    }
}


private fun part2(grid: Grid<Char>): Int {
    val mas = listOf('M', 'A', 'S')

    return grid.count { (point, char) ->
        if (char != 'A') {
            return@count false
        }

        val upLeftToBottomRight =
            grid.walkInDirection(startAt = point.go(Direction.UpLeft), steps = 2, direction = Direction.DownRight)
        val downRightToUpLeft =
            grid.walkInDirection(startAt = point.go(Direction.DownRight), steps = 2, direction = Direction.UpLeft)

        val upRightToDownLeft =
            grid.walkInDirection(startAt = point.go(Direction.UpRight), steps = 2, direction = Direction.DownLeft)
        val downLeftToUpRight =
            grid.walkInDirection(startAt = point.go(Direction.DownLeft), steps = 2, direction = Direction.UpRight)

        val isMas1 = upLeftToBottomRight == mas || downRightToUpLeft == mas
        val isMas2 = upRightToDownLeft == mas || downLeftToUpRight == mas
        isMas1 && isMas2
    }
}

fun main() {
    val input = readFile("/4_1.txt")
    val grid = Grid(input.lines().map { it.toList() }.toList())
    measure("Day 4 Part 1") { assertAndReturn(part1(grid), 2378) }
    measure("Day 4 Part 2") { assertAndReturn(part2(grid), 1796) }
}
