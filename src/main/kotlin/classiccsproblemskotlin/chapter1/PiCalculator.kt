package classiccsproblemskotlin.chapter1

//Original translated to Kotlin
fun calculatePi(nTerms: Int) : Double {
    val numerator = 4.0
    var denominator = 1.0
    var operation = 1.0
    var pi = 0.0

    repeat(nTerms) {
        pi += operation * (numerator / denominator)
        denominator += 2.0
        operation *= -1.0
    }

    return pi
}

//As a sequence
fun leibniz() = sequence {
    val numerator = 4.0
    var denominator = 1.0
    var operation = 1.0
    var pi = 0.0

    while(true) {
        pi += operation * (numerator / denominator)
        denominator += 2.0
        operation *= -1.0

        yield(pi)
    }
}

fun main() {
    println(calculatePi(1000000))
    println(leibniz().drop(999999).first())
}
