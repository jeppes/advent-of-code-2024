package org.example

import org.example.Direction.*

private fun part1(grid: Grid<Char>): Int {
    val xmas = listOf('X', 'M', 'A', 'S')
    val allDirections = listOf(
        Up, Down, Left, Right,
        UpLeft, UpRight, DownLeft, DownRight
    )

    return grid.sumOf { (point, char) ->
        if (char == 'X') {
            allDirections
                .map { direction -> grid.walk(startAt = point, steps = 3, direction = direction) }
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
            grid.walk(startAt = point.go(UpLeft), steps = 2, direction = DownRight)
        val downRightToUpLeft =
            grid.walk(startAt = point.go(DownRight), steps = 2, direction = UpLeft)

        val upRightToDownLeft =
            grid.walk(startAt = point.go(UpRight), steps = 2, direction = DownLeft)
        val downLeftToUpRight =
            grid.walk(startAt = point.go(DownLeft), steps = 2, direction = UpRight)

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
