package org.example

data class Grid<T>(
    private val listOfList: List<List<T>>,
) : Iterable<Pair<Point, T>> {
    init {
        val columnCount = listOfList.firstOrNull()?.size ?: 0
        require(listOfList.all { it.size == columnCount }) {
            "All rows in grid must have the same number of column ($columnCount)"
        }
    }

    override fun iterator(): Iterator<Pair<Point, T>> {
        return object : Iterator<Pair<Point, T>> {
            private var row = 0
            private var column = 0

            override fun hasNext(): Boolean {
                return row < listOfList.size
            }

            override fun next(): Pair<Point, T> {
                val point = Point(row = row, column = column)
                val value = listOfList[row][column]

                if (column == listOfList[row].size - 1) {
                    row++
                    column = 0
                } else {
                    column++
                }

                return Pair(point, value)
            }
        }
    }

    operator fun get(point: Point): T {
        return listOfList[point.row][point.column]
    }

    private fun getOrNull(point: Point): T? {
        return listOfList.getOrNull(point.row)?.getOrNull(point.column)
    }

    fun walk(
        startAt: Point,
        steps: Int,
        direction: Direction,
    ): List<T?> {
        val points = (1..steps).fold(listOf(startAt)) { points, _ -> points + points.last().go(direction) }
        return points.mapNotNull(::getOrNull)
    }
}