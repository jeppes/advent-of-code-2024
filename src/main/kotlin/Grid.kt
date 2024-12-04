package org.example

data class Grid<T>(
    private val underlying: List<List<T>>,
) : Iterable<Pair<Point, T>> {
    override fun iterator(): Iterator<Pair<Point, T>> {
        return object : Iterator<Pair<Point, T>> {
            private var row = 0
            private var column = 0

            override fun hasNext(): Boolean {
                return row < underlying.size
            }

            override fun next(): Pair<Point, T> {
                val point = Point(row = row, column = column)
                val value = underlying[row][column]

                if (column == underlying[row].size - 1) {
                    row++
                    column = 0
                } else {
                    column++
                }

                return point to value
            }
        }
    }

    fun atOrNull(point: Point): T? {
        return underlying.getOrNull(point.row)?.getOrNull(point.column)
    }

    fun walkInDirection(
        startAt: Point,
        steps: Int,
        direction: Direction,
    ): List<T?> {
        val points = (1..steps).fold(listOf(startAt)) { points, _ -> points + points.last().go(direction) }
        return points.mapNotNull(this::atOrNull)
    }
}