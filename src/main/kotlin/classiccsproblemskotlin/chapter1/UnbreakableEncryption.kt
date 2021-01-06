package classiccsproblemskotlin.chapter1

import kotlin.experimental.xor
import kotlin.random.Random

typealias KeyPair = Pair<ByteArray, ByteArray>

fun randomKey(length: Int) : ByteArray = Random.nextBytes(length)

fun encrypt(original: String): KeyPair {
    val bytes = original.toByteArray()
    val key = randomKey(bytes.size)

    bytes.indices.forEach { bytes[it] = bytes[it] xor key[it] }

    return bytes to key
}

fun decrypt(kp: KeyPair) : String {
    val bytes = ByteArray(kp.first.size)

    kp.first.indices.forEach { bytes[it] = kp.first[it] xor kp.second[it] }

    return String(bytes)
}

fun main() {
    val kp = encrypt("One Time Pad!")
    val result = decrypt(kp)
    println(result)
}
