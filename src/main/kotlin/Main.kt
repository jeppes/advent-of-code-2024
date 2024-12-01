package org.example

fun main() {}

object Main

fun readFile(name: String): String {
  return Main::class.java.getResource(name)!!.readText()
}
