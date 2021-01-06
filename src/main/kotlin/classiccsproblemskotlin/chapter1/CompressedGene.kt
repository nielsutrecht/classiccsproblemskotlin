package classiccsproblemskotlin.chapter1

import java.util.*

fun compress(gene: String) : Pair<BitSet, Int> {
    val bitSet = BitSet(gene.length * 2)
    for(i in gene.indices) {
        val firstLocation = 2 * i
        val secondLocation = 2 * i + 1
        val (first, second) = when (gene[i].toUpperCase()) {
            'A' -> false to false
            'C' -> false to true
            'G' -> true to false
            'T' -> true to true
            else -> throw IllegalArgumentException("${gene[i]} should be A, C, G or T")
        }
        bitSet.set(firstLocation, first)
        bitSet.set(secondLocation, second)
    }
    return bitSet to gene.length
}

fun decompress(bitSet: BitSet, length: Int) : String =
    (0 until length * 2).asSequence().map { if(bitSet[it]) 1 else 0 }
            .chunked(2)
            .map { (first, second) -> (first shl 1) + second }
            .map { when(it) {
                0b00 -> 'A'
                0b01 -> 'C'
                0b10 -> 'G'
                else -> 'T'
            } }.joinToString("")

fun main() {
    val original = "TAGGGATTAACCGTTATATATATATAGCCATGGATCGATTATATAGGGATTAACCGTTATATATATATAGCCATGGATCGATTATA"
    val (compressed, length) = compress(original)
    val decompressed = decompress(compressed, length)
    println(decompressed)
    println("original is the same as decompressed: ${original == decompressed}")
}
