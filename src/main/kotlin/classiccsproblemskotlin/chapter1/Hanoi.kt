package classiccsproblemskotlin.chapter1

typealias Tower = java.util.Stack<Int>

class Hanoi(private val numDiscs: Int) {
    val towerA = Tower()
    val towerB = Tower()
    val towerC = Tower()

    init {
        (1 .. numDiscs).forEach(towerA::push)
    }

    fun solve() {
        move(towerA, towerC, towerB, numDiscs)
    }

    private fun move(begin: Tower, end: Tower, temp: Tower, n: Int) {
        if(n == 1) {
            end.push(begin.pop())
        } else {
            move(begin, temp, end, n - 1)
            move(begin, end, temp, 1)
            move(temp, end, begin,n - 1)
        }
    }
}

fun main() {
    val hanoi = Hanoi(3)
    hanoi.solve()

    println(hanoi.towerA)
    println(hanoi.towerB)
    println(hanoi.towerC)
}
