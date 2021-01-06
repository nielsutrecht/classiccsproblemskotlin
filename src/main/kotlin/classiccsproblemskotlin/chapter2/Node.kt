package classiccsproblemskotlin.chapter2

data class Node<T> (
        val state: T,
        var parent: Node<T>?,
        var cost: Double = 0.0,
        val heuristic: Double = 0.0) : Comparable<Node<T>> {

    override fun compareTo(other: Node<T>): Int =
        (cost + heuristic).compareTo(other.cost + other.heuristic)

    fun toPath() : List<T> {
        val path = mutableListOf(state)
        var node = this
        while(node.parent != null) {
            node = node.parent!!
            path.add(0, node.state)
        }

        return path
    }
}
