package classiccsproblemskotlin.chapter3

class MapColoringConstraint(private val place1: String, private val place2: String) :
        Constraint<String, String>(listOf(place1, place2)) {

    override fun satisfied(assignment: Map<String, String>): Boolean {
        if (!assignment.contains(place1) || !assignment.contains(place2)) {
            return true
        }

        return assignment.getValue(place1) != assignment.getValue(place2)
    }
}

fun main() {
    val variables = listOf("Western Australia", "Northern Territory", "South Australia",
            "Queensland", "New South Wales", "Victoria", "Tasmania")

    val domains = variables.map { it to listOf("red", "green", "blue") }.toMap()

    val csp = CSP(variables, domains)

    csp += MapColoringConstraint("Western Australia", "Northern Territory")
    csp += MapColoringConstraint("Western Australia", "South Australia")
    csp += MapColoringConstraint("South Australia", "Northern Territory")
    csp += MapColoringConstraint("Queensland", "Northern Territory")
    csp += MapColoringConstraint("Queensland", "South Australia")
    csp += MapColoringConstraint("Queensland", "New South Wales")
    csp += MapColoringConstraint("New South Wales", "South Australia")
    csp += MapColoringConstraint("Victoria", "South Australia")
    csp += MapColoringConstraint("Victoria", "New South Wales")
    csp += MapColoringConstraint("Victoria", "Tasmania")

    val solution = csp.backtrackingSearch()

    println(solution)
}
