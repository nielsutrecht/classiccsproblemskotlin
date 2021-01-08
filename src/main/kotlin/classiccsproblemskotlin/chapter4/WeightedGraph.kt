package classiccsproblemskotlin.chapter4

import java.util.*

class WeightedGraph<V> : Graph<V, WeightedEdge> {
    constructor() : super()
    constructor(vertices: Collection<V>) : super(vertices)

    operator fun plusAssign(edge: WeightedEdge) {
        edges[edge.u].add(edge)
        edges[edge.v].add(edge.reversed())
    }

    fun addEdge(u: Int, v: Int, weight: Number) {
        this += WeightedEdge(u, v, weight.toDouble())
    }

    fun addEdge(first: V, second: V, weight: Number) {
        this += WeightedEdge(indexOf(first), indexOf(second), weight.toDouble())
    }

    override fun toString(): String {
        return vertices.mapIndexed { index, v ->
            "$v -> " + edgesOf(index)
                    .map { e -> "(${this[e.v]}, ${e.weight})" }
        }
                .joinToString("\n")
    }

    fun mst(start: Int = 0) : List<WeightedEdge> {
        val result = mutableListOf<WeightedEdge>()

        if(start !in vertices.indices) {
            return result
        }

        val pq = PriorityQueue<WeightedEdge>()
        val visited = BooleanArray(vertexCount)

        fun visit(index: Int) {
            visited[index] = true
            edgesOf(index).filterNot { edge -> visited[edge.v] }.forEach(pq::offer)
        }

        visit(start)

        while(pq.isNotEmpty()) {
            val edge = pq.poll()
            if(visited[edge.v]) {
                continue
            }
            result += edge
            visit(edge.v)
        }
        return result
    }

    fun printWeightedPath(path: Collection<WeightedEdge>) {
        path.forEach { edge ->
            println("${this[edge.u]} ${edge.weight} > ${this[edge.v]}")
        }
        println("Total weight: ${path.totalWeight()}")
    }
}

fun main() {
    val cityGraph2 = WeightedGraph(listOf("Seattle", "San Francisco", "Los Angeles", "Riverside", "Phoenix",
            "Chicago", "Boston", "New York", "Atlanta", "Miami", "Dallas", "Houston", "Detroit", "Philadelphia",
            "Washington"))

    cityGraph2.addEdge("Seattle", "Chicago", 1737)
    cityGraph2.addEdge("Seattle", "San Francisco", 678)
    cityGraph2.addEdge("San Francisco", "Riverside", 386)
    cityGraph2.addEdge("San Francisco", "Los Angeles", 348)
    cityGraph2.addEdge("Los Angeles", "Riverside", 50)
    cityGraph2.addEdge("Los Angeles", "Phoenix", 357)
    cityGraph2.addEdge("Riverside", "Phoenix", 307)
    cityGraph2.addEdge("Riverside", "Chicago", 1704)
    cityGraph2.addEdge("Phoenix", "Dallas", 887)
    cityGraph2.addEdge("Phoenix", "Houston", 1015)
    cityGraph2.addEdge("Dallas", "Chicago", 805)
    cityGraph2.addEdge("Dallas", "Atlanta", 721)
    cityGraph2.addEdge("Dallas", "Houston", 225)
    cityGraph2.addEdge("Houston", "Atlanta", 702)
    cityGraph2.addEdge("Houston", "Miami", 968)
    cityGraph2.addEdge("Atlanta", "Chicago", 588)
    cityGraph2.addEdge("Atlanta", "Washington", 543)
    cityGraph2.addEdge("Atlanta", "Miami", 604)
    cityGraph2.addEdge("Miami", "Washington", 923)
    cityGraph2.addEdge("Chicago", "Detroit", 238)
    cityGraph2.addEdge("Detroit", "Boston", 613)
    cityGraph2.addEdge("Detroit", "Washington", 396)
    cityGraph2.addEdge("Detroit", "New York", 482)
    cityGraph2.addEdge("Boston", "New York", 190)
    cityGraph2.addEdge("New York", "Philadelphia", 81)
    cityGraph2.addEdge("Philadelphia", "Washington", 123)

    println(cityGraph2)

    val mst = cityGraph2.mst()
    cityGraph2.printWeightedPath(mst)
}
