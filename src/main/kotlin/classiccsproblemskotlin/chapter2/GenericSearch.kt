package classiccsproblemskotlin.chapter2

import java.util.*

fun <T : Comparable<T>> List<T>.linearContains(key: T) : Boolean =
    this.any { it.compareTo(key) == 0 }

fun <T : Comparable<T>> List<T>.binaryContains(key: T) : Boolean {
    var low = 0
    var high = size - 1

    while (low <= high) {
        val middle = (low + high) / 2
        val comparison = this[middle].compareTo(key)

        when {
            comparison < 0 -> { low = middle + 1 }
            comparison > 0 -> { high = middle - 1 }
            else -> return true
        }
    }

    return false
}

fun <T> dfs(initial: T, goalTest: (T) -> Boolean, successors: (T) -> Sequence<T>) : Node<T>? {
    val frontier = ArrayDeque(listOf(Node(initial, null)))

    val explored = mutableSetOf<T>()
    explored += initial

    while(frontier.isNotEmpty()) {
        val currentNode = frontier.pop()
        if(goalTest(currentNode.state)) {
            return currentNode
        }
        successors(currentNode.state).filterNot(explored::contains).forEach { child ->
            explored += child
            frontier.push(Node(child, currentNode))
        }
    }

    return null
}

fun <T> bfs(initial: T, goalTest: (T) -> Boolean, successors: (T) -> Sequence<T>) : Node<T>? {
    val frontier = ArrayDeque(listOf(Node(initial, null)))

    val explored = mutableSetOf<T>()
    explored += initial

    while(frontier.isNotEmpty()) {
        val currentNode = frontier.poll()
        if(goalTest(currentNode.state)) {
            return currentNode
        }
        successors(currentNode.state).filterNot(explored::contains).forEach { child ->
            explored += child
            frontier.offer(Node(child, currentNode))
        }
    }

    return null
}


fun <T> aStar(initial: T, goalTest: (T) -> Boolean, successors: (T) -> Sequence<T>, heuristic: (T) -> Double) : Node<T>? {
    val frontier = PriorityQueue(listOf(
            Node(initial, null, 0.0, heuristic(initial)))
    )

    val explored = mutableMapOf<T, Double>()
    explored[initial] = 0.0

    while(frontier.isNotEmpty()) {
        val currentNode = frontier.poll()
        if(goalTest(currentNode.state)) {
            return currentNode
        }
        successors(currentNode.state).filterNot(explored::contains).forEach { child ->
            val newCost = currentNode.cost + 1
            if (!explored.containsKey(child) || explored[child]!! > newCost) {
                explored[child] = newCost
                frontier.offer(Node(child, currentNode, newCost, heuristic(child)))
            }
        }
    }

    return null
}

fun main() {
    println(listOf(1, 5, 15, 15, 15, 15, 20).linearContains(5)) //true
    println(listOf("a", "d", "e", "f", "z").binaryContains("f")) //true
    println(listOf("john", "mark", "ronald", "sarah").binaryContains("sheila")) //false
}
