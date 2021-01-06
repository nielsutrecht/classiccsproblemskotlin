package classiccsproblemskotlin.chapter1

fun fib2(n: Int) : Int = if(n < 2) n else fib2(n - 1) + fib2(n - 2)

fun main() {
    println(fib2(5))
    println(fib2(10))
}
