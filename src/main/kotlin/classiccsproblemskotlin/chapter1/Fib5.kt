package classiccsproblemskotlin.chapter1

fun fib5() = sequence {
    var last = 0L
    var next = 1L

    while(true) {
        yield(last)

        val oldLast = last
        last = next
        next += oldLast
    }
}

fun main() {
    fib5().take(41).forEach(::println)
}
