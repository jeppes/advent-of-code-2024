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

    val rowCount = listOfList.size
    val columnCount = listOfList.firstOrNull()?.size ?: 0

    override fun iterator(): Iterator<Pair<Point, T>> {
        return listOfList.indices.flatMap { row ->
            listOfList[row].indices.map { column ->
                Pair(
                    Point(row = row, column = column),
                    listOfList[row][column]
                )
            }
        }.iterator()
    }

    operator fun get(point: Point): T {
        return listOfList[point.row][point.column]
    }

    fun getOrNull(point: Point): T? {
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