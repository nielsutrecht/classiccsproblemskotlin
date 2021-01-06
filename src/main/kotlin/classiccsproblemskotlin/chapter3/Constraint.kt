package classiccsproblemskotlin.chapter3

abstract class Constraint<V, D>(val variables: List<V>) {
    abstract fun satisfied(assignment: Map<V, D>): Boolean
}
