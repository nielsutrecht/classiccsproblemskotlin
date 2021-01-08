package classiccsproblemskotlin.chapter4

class WeightedEdge(u: Int, v: Int, val weight: Double) : Edge(u, v), Comparable<WeightedEdge> {
    override fun reversed(): WeightedEdge {
        return WeightedEdge(v, u, weight)
    }

    override fun toString(): String = "$u -$weight-> $v"

    override fun compareTo(other: WeightedEdge): Int = this.weight.compareTo(other.weight)
}

fun Collection<WeightedEdge>.totalWeight() = this.sumByDouble { it.weight }
