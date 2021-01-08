package classiccsproblemskotlin.chapter3

class SendMoreMoneyConstraint(private val letters: List<Char>) : Constraint<Char, Int>(letters) {
    override fun satisfied(assignment: Map<Char, Int>): Boolean {
        if(assignment.values.distinct().size < assignment.size) {
            return false
        }

        if(assignment.size == letters.size) {
            val s = assignment.getValue('S')
            val e = assignment.getValue('E')
            val n = assignment.getValue('N')
            val d = assignment.getValue('D')
            val m = assignment.getValue('M')
            val o = assignment.getValue('O')
            val r = assignment.getValue('R')
            val y = assignment.getValue('Y')

            val send = s * 1000 + e * 100 + n * 10 + d
            val more = m * 1000 + o * 100 + r * 10 + e
            val money = m * 10000 + o * 1000 + n * 100 + e * 10 + y

            return send + more == money
        }
        return true
    }
}

fun main() {
    val letters = listOf('S', 'E', 'N', 'D', 'M', 'O', 'R', 'Y')
    val domain = (letters.map { it to (0 .. 9).toList() } + ('M' to listOf(1))).toMap()
    val csp = CSP(letters, domain)
    csp += SendMoreMoneyConstraint(letters)

    val solution = csp.backtrackingSearch()

    if(solution == null) {
        println("No solution found!")
    } else {
        println(solution)
    }
}
