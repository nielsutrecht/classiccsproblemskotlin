package classiccsproblemskotlin.chapter1

object Fib3 {
    private val memo = mutableMapOf(0 to 0L, 1 to 1L)

    fun fib(n: Int) : Long {
        if(!memo.contains(n)) {
            memo[n] = fib(n - 1) + fib(n -2)
        }
        return memo.getValue(n)
    }
}

fun main() {
    println(Fib3.fib(5))
    println(Fib3.fib(40))
}
