package org.example

private fun part1(grid: Grid<Char>): Int {
    val xmas = listOf('X', 'M', 'A', 'S')
    val allDirections = listOf(
        Direction.Up, Direction.Down, Direction.Left, Direction.Right,
        Direction.UpLeft, Direction.UpRight, Direction.DownLeft, Direction.DownRight
    )

    var sum = 0
    grid.forEach { point, char ->
        if (char == 'X') {
            sum += allDirections
                .map { direction -> grid.walkInDirection(startAt = point, steps = 3, direction = direction) }
                .count { xmas == it }
        }
    }
    return sum
}


private fun part2(grid: List<List<Char>>): Int {
    val mas = listOf('M', 'A', 'S')

    var sum = 0
    grid.forEach { point, char ->
        if (char != 'A') {
            return@forEach
        }

        val upLeftToBottomRight =
            grid.walkInDirection(startAt = point.go(Direction.UpLeft), steps = 2, direction = Direction.DownRight)
        val downRightToUpLeft =
            grid.walkInDirection(startAt = point.go(Direction.DownRight), steps = 2, direction = Direction.UpLeft)

        val upRightToDownLeft =
            grid.walkInDirection(startAt = point.go(Direction.UpRight), steps = 2, direction = Direction.DownLeft)
        val downLeftToUpRight =
            grid.walkInDirection(startAt = point.go(Direction.DownLeft), steps = 2, direction = Direction.UpRight)

        if ((upLeftToBottomRight == mas || downRightToUpLeft == mas)
            && (upRightToDownLeft == mas || downLeftToUpRight == mas)
        ) {
            sum += 1
        }
    }
    return sum
}

fun main() {
    val input = readFile("/4_1.txt")
    val grid = input.lines().map { it.toList() }.toList()
    measure("Day 4 Part 1") { assertAndReturn(part1(grid), 2378) }
    measure("Day 4 Part 2") { assertAndReturn(part2(grid), 1796) }
}
