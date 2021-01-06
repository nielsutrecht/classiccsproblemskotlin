package classiccsproblemskotlin.chapter2

fun main() {
    val start = MCState(MCState.MAX_NUM, MCState.MAX_NUM, true)
    val solution = bfs(start, MCState::goalTest, MCState::succesors)

    if(solution == null) {
        println("No solution found!")
    } else {
        val path = solution.toPath()
        path.forEach(::println)
    }
}
