package classiccsproblemskotlin.chapter4

open class Edge(val u: Int, val v: Int) {
    open fun reversed() : Edge = Edge(v, u)

    override fun toString(): String = "$u -> $v"
}
