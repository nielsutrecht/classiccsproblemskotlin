package classiccsproblemskotlin.chapter3

class WordGrid(val rows: Int, val columns: Int) {
    val grid = CharArray(rows * columns) { randomAlphabetic.first() }

    data class GridLocation(val row: Int, val column: Int)

    fun mark(word: String, locations: List<GridLocation>) {
        locations.zip(word.toList()).forEach { (loc, c) ->
            grid[loc.column + loc.row * columns] = c
        }
    }

    override fun toString(): String = grid.toList().chunked(columns)
            .joinToString("\n") { it.joinToString("") }

    fun generateDomain(word: String): List<List<GridLocation>> {
        val domain = mutableListOf<List<GridLocation>>()

        val length = word.length
        (0 until rows).flatMap { r -> (0 until columns).map { c -> r to c } }.forEach { (row, column) ->
            if (column + length <= columns) {
                // left to right
                domain += fillRight(row, column, length)
                // diagonal towards bottom right
                if (row + length <= rows) {
                    domain += fillDiagonalRight(row, column, length)
                }
            }
            if (row + length <= rows) {
                // top to bottom
                domain += fillDown(row, column, length)
                // diagonal towards bottom left
                if (column - length >= 0) {
                    domain += fillDiagonalLeft(row, column, length)
                }
            }
        }

        return domain
    }

    private fun fillRight(row: Int, column: Int, length: Int): List<GridLocation> =
            (column until column + length).map { c -> GridLocation(row, c) }

    private fun fillDiagonalRight(row: Int, column: Int, length: Int): List<GridLocation> =
            (column until column + length).mapIndexed { index, c -> GridLocation(row + index, c) }

    private fun fillDown(row: Int, column: Int, length: Int): List<GridLocation> =
            (row until row + length).map { r -> GridLocation(r, column) }

    private fun fillDiagonalLeft(row: Int, column: Int, length: Int): List<GridLocation> =
            (row until row + length).mapIndexed { index, r -> GridLocation(r, column + index) }

    companion object {
        const val ALPHABET_LENGTH = 26
        const val FIRST_LETTER = 'A'

        val randomAlphabetic = sequence {
            while (true) {
                yield(FIRST_LETTER + (0 until ALPHABET_LENGTH).random())
            }
        }
    }
}

fun main() {
    val wordGrid = WordGrid(9, 9)
    println(wordGrid)
    println()
    val words = listOf("MATTHEW", "JOE", "MARY", "SARAH", "SALLY")
    val domains = words.map { it to wordGrid.generateDomain(it) }.toMap()

    val csp: CSP<String, List<WordGrid.GridLocation>> = CSP(words, domains)
    csp += WordSearchConstraint(words)

    val solution = csp.backtrackingSearch()

    if(solution == null) {
        println("No solution found!")
    } else {
        solution.forEach { (word, locations) ->
            val reverse = listOf(true, false).random()
            wordGrid.mark(word, if(reverse) locations.reversed() else locations)
        }
        println(wordGrid)
    }

}
