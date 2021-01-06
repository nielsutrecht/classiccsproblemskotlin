package classiccsproblemskotlin.chapter2


data class Gene(val codons: List<Codon>) {
    constructor(gene: String) : this(gene
            .also { assert(gene.length % 3 == 0) { "Gene length should be a multiple of 3" } }
            .map { Nucleotide.valueOf(it.toString()) }
            .chunked(3).map { (first, second, third) -> Codon(first, second, third) })

    enum class Nucleotide {
        A, C, G, T
    }

    data class Codon(val first: Nucleotide, val second: Nucleotide, val third: Nucleotide) : Comparable<Codon> {
        constructor(codon: String) : this(
                Nucleotide.valueOf(codon[0].toString()),
                Nucleotide.valueOf(codon[1].toString()),
                Nucleotide.valueOf(codon[2].toString()))

        override fun compareTo(other: Codon): Int =
                compareBy<Codon>({ it.first }, { it.second }, { it.third }).compare(this, other)
    }

    fun linearContains(codon: Codon): Boolean {
        for (i in codons.indices) {
            if (codon.compareTo(codons[i]) == 0) {
                return true
            }
        }

        return false
    }

    fun binaryContains(key: Codon): Boolean {
        val sorted = codons.sorted()

        var low = 0
        var high = sorted.size - 1

        while (low <= high) {
            val middle = (low + high) / 2
            val comparison = sorted[middle].compareTo(key)

            when {
                comparison < 0 -> low = middle + 1
                comparison > 0 -> high = middle - 1
                else -> return true
            }
        }

        return false
    }
}

fun main() {
    val geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATACCCTAGGACTCCCTTT"
    val myGene = Gene(geneStr)

    val acg = Gene.Codon("ACG")
    val gat = Gene.Codon("GAT")

    println(myGene.linearContains(acg)) // true
    println(myGene.linearContains(gat)) // false
    println(myGene.binaryContains(acg)) // true
    println(myGene.binaryContains(gat)) // false
}
