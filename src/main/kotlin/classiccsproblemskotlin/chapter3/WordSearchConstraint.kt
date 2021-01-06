package classiccsproblemskotlin.chapter3

class WordSearchConstraint(words: List<String>) : Constraint<String, List<WordGrid.GridLocation>>(words) {
    override fun satisfied(assignment: Map<String, List<WordGrid.GridLocation>>): Boolean {
        val allLocations = assignment.values.flatten()

        return allLocations.distinct().size == allLocations.size
    }
}
