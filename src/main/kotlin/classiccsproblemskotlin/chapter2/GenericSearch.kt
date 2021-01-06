package classiccsproblemskotlin.chapter2

fun <T : Comparable<T>> List<T>.linearContains(key: T) : Boolean =
    this.any { it.compareTo(key) == 0 }

fun <T : Comparable<T>> List<T>.binaryContains(key: T) : Boolean {
    var low = 0
    var high = size - 1

    while (low <= high) {
        val middle = (low + high) / 2
        val comparison = this[middle].compareTo(key)

        when {
            comparison < 0 -> { low = middle + 1 }
            comparison > 0 -> { high = middle - 1 }
            else -> return true
        }
    }

    return false
}

fun main() {
    println(listOf(1, 5, 15, 15, 15, 15, 20).linearContains(5)) //true
    println(listOf("a", "d", "e", "f", "z").binaryContains("f")) //true
    println(listOf("john", "mark", "ronald", "sarah").binaryContains("sheila")) //false
}
