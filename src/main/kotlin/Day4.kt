package org.example

fun <T> List<List<T>>.atOrNull(point: Point): T? {
    return this.getOrNull(point.row)?.getOrNull(point.column)
}


fun fourStepsInAllDirections(grid: List<List<Char>>, point: Point): List<List<Char>> {
    val upLeft = (1..3).fold(listOf(point)) { points, _ -> points + points.last().goUpLeft() }
    val up = (1..3).fold(listOf(point)) { points, _ -> points + points.last().goUp() }
    val upRight = (1..3).fold(listOf(point)) { points, _ -> points + points.last().goUpRight() }
    val right = (1..3).fold(listOf(point)) { points, _ -> points + points.last().goRight() }
    val downRight = (1..3).fold(listOf(point)) { points, _ -> points + points.last().goDownRight() }
    val down = (1..3).fold(listOf(point)) { points, _ -> points + points.last().goDown() }
    val downLeft = (1..3).fold(listOf(point)) { points, _ -> points + points.last().goDownLeft() }
    val left = (1..3).fold(listOf(point)) { points, _ -> points + points.last().goLeft() }

    return listOf(
        upLeft,
        up,
        upRight,
        right,
        downRight,
        down,
        downLeft,
        left
    ).map { points -> points.mapNotNull { point -> grid.atOrNull(point) } }


}

private fun part1(input: String): Int {
    val grid = input.lines().map { it.toList() }.toList()

    var sum = 0

    val xmas = listOf('X', 'M', 'A', 'S')
    for (row in grid.indices) {
        for (column in grid[row].indices) {
            if (grid.atOrNull(Point(row = row, column = column)) == 'X') {
                sum += fourStepsInAllDirections(grid, Point(row = row, column = column)).count { xmas == it }
            }
        }
    }

    return sum
}

private fun isMasMas(grid: List<List<Char>>, point: Point): Boolean {
    val upLeft = point.goUpLeft()
    val upRight = point.goUpRight()
    val downRight = point.goDownRight()
    val downLeft = point.goDownLeft()

    val mas = listOf('M', 'A', 'S')
    if (point.row == 1 && point.column == 0) {
        println(
            listOf(grid.atOrNull(upLeft), grid.atOrNull(point), grid.atOrNull(downRight))
        )
    }

    val first = listOf(grid.atOrNull(upLeft), grid.atOrNull(point), grid.atOrNull(downRight)) == mas
            || listOf(grid.atOrNull(downRight), grid.atOrNull(point), grid.atOrNull(upLeft)) == mas
    val second = listOf(grid.atOrNull(upRight), grid.atOrNull(point), grid.atOrNull(downLeft)) == mas
            || listOf(grid.atOrNull(downLeft), grid.atOrNull(point), grid.atOrNull(upRight)) == mas

    return first && second
}


private fun part2(input: String): Int {
    val grid = input.lines().map { it.toList() }.toList()

    var sum = 0

    for (row in grid.indices) {
        for (column in grid[row].indices) {
            if (grid.atOrNull(Point(row = row, column = column)) == 'A') {
                sum += if (isMasMas(grid, Point(row = row, column = column))) 1 else 0
            }
        }
    }

    return sum
}

fun main() {
    val input = readFile("/4_1.txt")
    measure("Day 4 Part 1") { assertAndReturn(part1(input), 2378) }
    measure("Day 4 Part 2") { assertAndReturn(part2(input), 1796) }
}
