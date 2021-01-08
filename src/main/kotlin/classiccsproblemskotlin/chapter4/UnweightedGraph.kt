package classiccsproblemskotlin.chapter4

import classiccsproblemskotlin.chapter2.bfs

class UnweightedGraph<V> : Graph<V, Edge> {

    constructor() : super()
    constructor(vertices: Collection<V>) : super(vertices)

    operator fun plusAssign(edge: Edge) {
        edges[edge.u].add(edge)
        edges[edge.v].add(edge.reversed())
    }

    fun addEdge(u: Int, v: Int) {
        this += Edge(u, v)
    }

    fun addEdge(first: V, second: V) {
        this += Edge(indexOf(first), indexOf(second))
    }
}

fun main() {
    val cityGraph = UnweightedGraph(listOf(
            "Seattle", "San Francisco", "Los Angeles", "Riverside", "Phoenix", "Chicago", "Boston", "New York",
            "Atlanta", "Miami", "Dallas", "Houston", "Detroit", "Philadelphia", "Washington"
    ))

    cityGraph.addEdge("Seattle", "Chicago")
    cityGraph.addEdge("Seattle", "San Francisco")
    cityGraph.addEdge("San Francisco", "Riverside")
    cityGraph.addEdge("San Francisco", "Los Angeles")
    cityGraph.addEdge("Los Angeles", "Riverside")
    cityGraph.addEdge("Los Angeles", "Phoenix")
    cityGraph.addEdge("Riverside", "Phoenix")
    cityGraph.addEdge("Riverside", "Chicago")
    cityGraph.addEdge("Phoenix", "Dallas")
    cityGraph.addEdge("Phoenix", "Houston")
    cityGraph.addEdge("Dallas", "Chicago")
    cityGraph.addEdge("Dallas", "Atlanta")
    cityGraph.addEdge("Dallas", "Houston")
    cityGraph.addEdge("Houston", "Atlanta")
    cityGraph.addEdge("Houston", "Miami")
    cityGraph.addEdge("Atlanta", "Chicago")
    cityGraph.addEdge("Atlanta", "Washington")
    cityGraph.addEdge("Atlanta", "Miami")
    cityGraph.addEdge("Miami", "Washington")
    cityGraph.addEdge("Chicago", "Detroit")
    cityGraph.addEdge("Detroit", "Boston")
    cityGraph.addEdge("Detroit", "Washington")
    cityGraph.addEdge("Detroit", "New York")
    cityGraph.addEdge("Boston", "New York")
    cityGraph.addEdge("New York", "Philadelphia")
    cityGraph.addEdge("Philadelphia", "Washington")

    println(cityGraph)

    val bfsResult = bfs("Boston", {it == "Miami"}, { n -> cityGraph.neighborsOf(n).asSequence() })

    if(bfsResult == null) {
        println("No solution found using breadth-first search!")
    } else {
        val path = bfsResult.toPath()
        println("Path from Boston to Miami:")
        println(path)
    }
}
