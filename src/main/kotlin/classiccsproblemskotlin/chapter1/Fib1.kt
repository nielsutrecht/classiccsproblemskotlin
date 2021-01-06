package classiccsproblemskotlin.chapter1

fun fib1(n: Int) : Int = fib1(n - 1) + fib1(n - 2)

fun main() {
    fib1(5)
}
