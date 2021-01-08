package classiccsproblemskotlin.chapter4

abstract class Graph<V, E : Edge> {
    protected val vertices = mutableListOf<V>()
    protected val edges = mutableListOf<MutableList<E>>()

    constructor()

    constructor(vertices: Collection<V>) {
        this.vertices += vertices
        vertices.forEach { _ ->
            edges += mutableListOf<E>()
        }
    }

    val vertexCount: Int
        get() = vertices.size

    val edgeCount: Int
        get() = edges.sumBy { it.size }

    fun addVertex(vertex: V) : Int {
        vertices += vertex
        edges += mutableListOf<E>()

        return vertexCount - 1
    }

    operator fun get(index: Int) : V = vertices[index]
    fun indexOf(vertex: V) : Int = vertices.indexOf(vertex)
    fun neighborsOf(index: Int) : List<V> = edges[index].map { edge -> this[edge.v] }
    fun neighborsOf(vertex: V) : List<V> = neighborsOf(indexOf(vertex))
    fun edgesOf(index: Int) : List<E> = edges[index]
    fun edgesOf(vertex: V) : List<E> = edges[indexOf(vertex)]

    override fun toString(): String {
        return vertices.mapIndexed { index, v -> "$v -> " + neighborsOf(index).toString() }
                .joinToString("\n")
    }
}
