package classiccsproblemskotlin.chapter1

fun fib4(n: Int) : Long {
    var last = 0L
    var next = 1L
    repeat(n) {
        val oldLast = last
        last = next
        next += oldLast
    }

    return last
}

fun main() {
    println(fib4(5))
    println(fib4(40))
}
