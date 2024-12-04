package org.example


fun fourStepsInAllDirections(grid: List<List<Char>>, point: Point): List<List<Char>> {
    return listOf(
        grid.walkInDirection(startAt = point, steps = 3, direction = Direction.Up),
        grid.walkInDirection(startAt = point, steps = 3, direction = Direction.Down),
        grid.walkInDirection(startAt = point, steps = 3, direction = Direction.Left),
        grid.walkInDirection(startAt = point, steps = 3, direction = Direction.Right),
        grid.walkInDirection(startAt = point, steps = 3, direction = Direction.UpLeft),
        grid.walkInDirection(startAt = point, steps = 3, direction = Direction.UpRight),
        grid.walkInDirection(startAt = point, steps = 3, direction = Direction.DownLeft),
        grid.walkInDirection(startAt = point, steps = 3, direction = Direction.DownRight),
    ).map { it.filterNotNull() }
}

private fun part1(grid: Grid<Char>): Int {
    var sum = 0

    val xmas = listOf('X', 'M', 'A', 'S')
    grid.forEach { point, char ->
        if (char == 'X') {
            sum += fourStepsInAllDirections(grid, point).count { xmas == it }
        }
    }

    return sum
}

private fun isMasMas(grid: List<List<Char>>, point: Point): Boolean {
    val mas = listOf('M', 'A', 'S')

    val upLeftToBottomRight =
        grid.walkInDirection(startAt = point.go(Direction.UpLeft), steps = 2, direction = Direction.DownRight)
    val downRightToUpLeft =
        grid.walkInDirection(startAt = point.go(Direction.DownRight), steps = 2, direction = Direction.UpLeft)

    val upRightToDownLeft =
        grid.walkInDirection(startAt = point.go(Direction.UpRight), steps = 2, direction = Direction.DownLeft)
    val downLeftToUpRight =
        grid.walkInDirection(startAt = point.go(Direction.DownLeft), steps = 2, direction = Direction.UpRight)

    return (upLeftToBottomRight == mas || downRightToUpLeft == mas) && (upRightToDownLeft == mas || downLeftToUpRight == mas)
}


private fun part2(grid: List<List<Char>>): Int {
    var sum = 0

    grid.forEach { point, char ->
        if (char == 'A') {
            sum += if (isMasMas(grid, point)) 1 else 0
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
