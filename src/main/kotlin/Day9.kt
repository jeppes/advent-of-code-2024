import org.example.assertAndReturn
import org.example.measure
import org.example.readFile

private fun part1(input: String): Long {
    val numbers = input.split("").filter { it.isNotBlank() }.map { it.toInt() }
    val array = Array<Int?>(numbers.sum()) { null }

    var i = 0
    for ((index, number) in numbers.withIndex()) {
        val isFile = index % 2 == 0
        if (isFile) {
            val targetI = i + number
            while (i < targetI) {
                array[i] = (index / 2)
                i++
            }
        } else {
            i += number
        }
    }


    var leftI = 0
    var rightI = array.size - 1
    while (leftI < array.size && rightI > leftI) {
        if (array[leftI] != null) {
            leftI++
        } else if (array[rightI] == null) {
            rightI--
        } else {
            require(array[leftI] == null)
            require(array[rightI] != null)
            array[leftI] = array[rightI]
            array[rightI] = null

            leftI++
        }
    }

    return array.withIndex().sumOf { (index, value) ->
        if (value != null) (index * value).toLong() else 0L
    }
}

data class FileToMove(
    val id: Int,
    val size: Int,
    val originalIndexOnDisk: Int,
)

private fun part2(input: String): Long {
    val numbers = input.split("").filter { it.isNotBlank() }.map { it.toInt() }
    val array = Array<Int?>(numbers.sum()) { null }

    var i = 0
    for ((index, number) in numbers.withIndex()) {
        val isFile = index % 2 == 0
        if (isFile) {
            val targetI = i + number
            while (i < targetI) {
                array[i] = (index / 2)
                i++
            }
        } else {
            i += number
        }
    }

    val filesToMove: MutableList<FileToMove> = numbers
        .filterIndexed { index, _ -> index % 2 == 0 }
        .withIndex()
        // First file cannot be moved so drop it
        .drop(1)
        .map { (id, size) ->
            val originalIndexOnDisk = array.indexOf(id)
            require(originalIndexOnDisk != -1)
            FileToMove(id = id, size = size, originalIndexOnDisk = originalIndexOnDisk)
        }
        .reversed()
        .toMutableList()

    for (file in filesToMove) {
        var leftI = 0
        while (leftI < file.originalIndexOnDisk) {
            while (leftI < file.originalIndexOnDisk && array[leftI] != null) {
                leftI++
            }

            val spaceLeft = run {
                var newI = leftI
                while (newI < array.size &&
                    array[newI] == null
                ) {
                    newI++
                }
                newI - leftI
            }

            if (file.size <= spaceLeft) {
                // move the file to the available space
                val targetI = leftI + file.size
                var offset = 0
                while (leftI < targetI) {
                    require(array[leftI] == null)
                    array[leftI] = file.id
                    require(array[file.originalIndexOnDisk + offset] == file.id)
                    array[file.originalIndexOnDisk + offset] = null
                    offset++
                    leftI++
                }

                break
            } else {
                leftI++
            }
        }
    }

    return array.withIndex().sumOf { (index, value) ->
        if (value != null) (index * value).toLong() else 0L
    }
}


fun main() {
    val input = readFile("/9_1.txt")

    measure("Day 9 Part 1") {
        assertAndReturn(part1(input), 6332189866718)
    }

    measure("Day 9 Part 2") {
        assertAndReturn(
            part2(input),
            6353648390778
        )
    }
}
