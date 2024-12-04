package org.example

typealias Grid<T> = List<List<T>>

fun <T> Grid<T>.atOrNull(point: Point): T? {
    return this.getOrNull(point.row)?.getOrNull(point.column)
}

fun <T> Grid<T>.forEach(block: (Point, T) -> Unit) {
    for (row in indices) {
        for (column in this[row].indices) {
            val point = Point(row = row, column = column)
            val t = atOrNull(point)!!
            block(point, t)
        }
    }
}

fun <T> Grid<T>.walkInDirection(
    startAt: Point,
    steps: Int,
    direction: Direction,
): List<T?> {
    val points = (1..steps).fold(listOf(startAt)) { points, _ -> points + points.last().go(direction) }
    return points.mapNotNull(this::atOrNull)
}