package org.example

enum class Direction {
    Left, Right, Up, Down, UpLeft, UpRight, DownLeft, DownRight
}

data class Point(val column: Int, val row: Int) {
    fun go(direction: Direction): Point {
        return when (direction) {
            Direction.Left -> copy(column = column - 1)
            Direction.Right -> copy(column = column + 1)
            Direction.Up -> copy(row = row - 1)
            Direction.Down -> copy(row = row + 1)
            Direction.UpLeft -> copy(column = column - 1, row = row - 1)
            Direction.UpRight -> copy(column = column + 1, row = row - 1)
            Direction.DownLeft -> copy(column = column - 1, row = row + 1)
            Direction.DownRight -> copy(column = column + 1, row = row + 1)
        }
    }

    val cardinalNeighbors: List<Point>
        get() {
            return listOf(Direction.Left, Direction.Right, Direction.Up, Direction.Down).map { go(it) }
        }

    val allNeighbors: List<Point>
        get() {
            return Direction.entries.map { go(it) }
        }

}
