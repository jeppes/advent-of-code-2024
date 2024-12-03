package org.example

import kotlin.time.measureTimedValue

inline fun <T> measure(label: String, crossinline block: () -> T): T {
    val result = measureTimedValue(block)
    println("[$label] ${result.value} (${result.duration})")
    return result.value
}
