package org.example

data class Point(val column: Int, val row: Int) {

    fun goLeft(): Point {
        return copy(column = column - 1)
    }

    fun goRight(): Point {
        return copy(column = column + 1)
    }

    fun goUp(): Point {
        return copy(row = row - 1)
    }

    fun goDown(): Point {
        return copy(row = row + 1)
    }

    fun goUpLeft(): Point {
        return copy(column = column - 1, row = row - 1)
    }

    fun goUpRight(): Point {
        return copy(column = column + 1, row = row - 1)
    }

    fun goDownLeft(): Point {
        return copy(column = column - 1, row = row + 1)
    }

    fun goDownRight(): Point {
        return copy(column = column + 1, row = row + 1)
    }

    val cardinalNeighbors: List<Point>
        get() {
            return listOf(goLeft(), goRight(), goUp(), goDown())
        }

    val allNeighbors: List<Point>
        get() {
            return listOf(goLeft(), goRight(), goUp(), goDown(), goUpLeft(), goUpRight(), goDownLeft(), goDownRight())
        }
}
