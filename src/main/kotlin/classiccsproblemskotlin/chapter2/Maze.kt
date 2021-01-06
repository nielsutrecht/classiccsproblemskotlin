package classiccsproblemskotlin.chapter2

import kotlin.math.abs
import kotlin.math.sqrt


class Maze(val rows: Int, val columns: Int, val start: MazeLocation, val goal: MazeLocation) {
    val grid = Array(rows * columns) { Cell.EMPTY }

    init {
        grid[start.toIndex()] = Cell.START
        grid[goal.toIndex()] = Cell.GOAL
    }

    constructor() : this(10, 10, MazeLocation(9, 0), MazeLocation(0, 9)) {
        randomlyFill(0.2)
    }

    override fun toString(): String = toString(emptySet())

    fun toString(pathLocations: Set<MazeLocation>): String =
            (0 until rows).joinToString("\n") { r ->
                (0 until columns).joinToString("") { c ->
                    val loc = MazeLocation(r, c)
                    if(loc in pathLocations && loc != start && loc != goal) {
                        Cell.PATH.code
                    } else {
                        grid[c + r * columns].code
                    }
                }
            }

    private fun randomlyFill(sparseness: Double) {
        grid.indices
                .filterNot { it == start.toIndex() || it == goal.toIndex() }
                .filter { Math.random() < sparseness }
                .forEach { grid[it] = Cell.BLOCKED }
    }

    private fun MazeLocation.toIndex() = column + row * columns
    private fun MazeLocation.neighbors() : Sequence<MazeLocation> = (-1..1).asSequence()
            .flatMap { r -> (-1..1).map { c -> MazeLocation(c, r) } }
            .filterNot { (c, r) -> r == 0 && c == 0 }
            .map { it + this }
            .filter(::inBounds)

    private operator fun MazeLocation.plus(other: MazeLocation) : MazeLocation =
            MazeLocation(row + other.row, column + other.column)

    private fun inBounds(ml: MazeLocation) : Boolean =
            ml.row >= 0 && ml.column >= 0 && ml.row < rows && ml.column < columns

    fun successors(ml: MazeLocation): Sequence<MazeLocation> =
                    ml.neighbors()
                    .filterNot { grid[it.toIndex()] == Cell.BLOCKED }
}

enum class Cell(val code: String) {
    EMPTY(" "),
    BLOCKED("X"),
    START("S"),
    GOAL("G"),
    PATH("*");

    override fun toString(): String = code
}

data class MazeLocation(val row: Int, val column: Int) {
    fun euclideanDistance(other: MazeLocation): Double {
        val xDist: Int = column - other.column
        val yDist: Int = row - other.row
        return sqrt((xDist * xDist + yDist * yDist).toDouble())
    }

    fun manhattanDistance(other: MazeLocation): Double {
        val xdist = abs(column - other.column)
        val ydist = abs(row - other.row)
        return (xdist + ydist).toDouble()
    }
}

fun main() {
    val maze = Maze()
    println(maze)
    println("-".repeat(10))

    fun solve(run: () -> Node<MazeLocation>?) {
        val solution = run()

        if(solution == null) {
            println("No solution found using depth-first search!")
        } else {
            println(maze.toString(solution.toPath().toSet()))
        }
        println("-".repeat(10))
    }

    solve {
        dfs(maze.start, { maze.goal == it }, { maze.successors(it) })
    }
    solve {
        bfs(maze.start, { maze.goal == it }, { maze.successors(it) })
    }
    solve {
        aStar(maze.start, { maze.goal == it }, { maze.successors(it) }, { maze.goal.manhattanDistance(it) })
    }
}
