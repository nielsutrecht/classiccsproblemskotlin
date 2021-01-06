package classiccsproblemskotlin.chapter2

data class MCState(val wm: Int, val wc: Int, val em: Int, val ec: Int, val boat: Boolean) {
    constructor(missionaries: Int, cannibals: Int, boat: Boolean) : this(
            missionaries,
            cannibals,
            MAX_NUM - missionaries,
            MAX_NUM - cannibals,
            boat
    )

    override fun toString(): String = """
        On the west bank there are $wm missionaries and $wc cannibals.
        On the east bank there are $em missionaries and $ec cannibals.
        The boat is on the ${if (boat) "west" else "east"} bank.""".trimMargin()

    fun isLegal(): Boolean = when {
        wm in 1 until wc -> false
        em in 1 until ec -> false
        else -> true
    }

    fun goalTest(): Boolean = isLegal() && em == MAX_NUM && ec == MAX_NUM

    companion object {
        const val MAX_NUM = 3
    }

    fun succesors(): Sequence<MCState> {
        val sucs = mutableListOf<MCState>()
        if (boat) { // boat on west bank
            if (wm > 1) sucs.add(MCState(wm - 2, wc, !boat))
            if (wm > 0) sucs.add(MCState(wm - 1, wc, !boat))
            if (wc > 1) sucs.add(MCState(wm, wc - 2, !boat))
            if (wc > 0) sucs.add(MCState(wm, wc - 1, !boat))
            if (wc > 0 && wm > 0) sucs.add(MCState(wm - 1, wc - 1, !boat))

        } else { // boat on east bank
            if (em > 1) sucs.add(MCState(wm + 2, wc, !boat))
            if (em > 0) sucs.add(MCState(wm + 1, wc, !boat))
            if (ec > 1) sucs.add(MCState(wm, wc + 2, !boat))
            if (ec > 0) sucs.add(MCState(wm, wc + 1, !boat))
            if (ec > 0 && em > 0) sucs.add(MCState(wm + 1, wc + 1, !boat))
        }
        return sucs.asSequence().filter(MCState::isLegal)
    }
}
