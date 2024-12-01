package org.example

import kotlin.math.abs

private fun parseLists(): Pair<List<Int>, List<Int>> {
  val lists = readFile("/1_1.txt").lines().map { it.split("   ") }
  val leftList = lists.map { it[0].toInt() }.sorted()
  val rightList = lists.map { it[1].toInt() }.sorted()

  return Pair(leftList, rightList)
}

private fun part1(): Int {
  val (leftList, rightList) = parseLists()
  return leftList.zip(rightList).sumOf { (left, right) -> abs(left - right) }
}

private fun part2(): Int {
  val (leftList, rightList) = parseLists()

  val counts = mutableMapOf<Int, Int>()
  for (number in rightList) {
    counts[number] = (counts[number] ?: 0) + 1
  }

  return leftList.sumOf { (counts[it] ?: 0) * it }
}

fun main() {
  require(measure("Day 1 part 1") { part1() } == 2166959)
  require(measure("Day 1 part 2") { part2() } == 23741109)
}
