package classiccsproblemskotlin.chapter3

class CSP<V, D>(private val variables: List<V>, private val domains: Map<V, List<D>>) {
    private val constraints = variables.map { it to mutableListOf<Constraint<V, D>>() }.toMap().toMutableMap()

    init {
        if(!variables.all(domains::containsKey))
            throw IllegalArgumentException("Every variable should have a domain assigned to it.")
    }

    operator fun plusAssign(constraint: Constraint<V, D>) {
        for(variable in constraint.variables) {
            if (!variables.contains(variable)) {
                throw  IllegalArgumentException("Variable in constraint not in CSP");
            }
            constraints.getValue(variable) += constraint
        }
    }

    fun consistent(variable: V, assignment: Map<V, D>): Boolean =
        constraints.getValue(variable).all { it.satisfied(assignment) }

    fun backtrackingSearch(assignment: Map<V, D> = emptyMap()) :  Map<V, D>? {
        if(assignment.size == variables.size) {
            return assignment
        }

        val unassigned = variables.first { !assignment.contains(it) }

        for(value : D in domains.getValue(unassigned)) {
            val localAssignment = assignment + (unassigned to value)

            if(consistent(unassigned, localAssignment)) {
                val result = backtrackingSearch(localAssignment)

                if(result != null) {
                    return result
                }
            }
        }

        return null
    }
}
