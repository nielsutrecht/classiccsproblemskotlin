package classiccsproblemskotlin.chapter3

import kotlin.math.abs

class QueensConstraint(val columns: List<Int>) : Constraint<Int, Int>(columns) {
    override fun satisfied(assignment: Map<Int, Int>): Boolean {
        assignment.forEach { (q1c, q1r) ->
            for(q2c in q1c + 1 .. columns.size) {
                val q2r = assignment[q2c] ?: return true
                if(q2r == q1r) {
                    return false
                }
                if (abs(q1r - q2r) == abs(q1c - q2c)) {
                    return false;
                }
            }
        }

        return true
    }
}

fun main() {
    val columns = (1 .. 8).toList()
    val rows = columns.map { it to (1 .. 8).toList() }.toMap()

    val csp = CSP(columns, rows)

    csp += QueensConstraint(columns)

    val solution = csp.backtrackingSearch()
    println(solution)
}
