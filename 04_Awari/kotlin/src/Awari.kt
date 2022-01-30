package com.pcholt.awari

import com.pcholt.awari.ToMove.Player
import com.pcholt.awari.ToMove.Computer

fun main() {
    println(AwariState(toMove = Player))
}

enum class ToMove(val permittedMoves: IntRange, val home: Int) {
    Player(1..6, 7),
    Computer(8..13, 0);
    val opponent get() = if (this == Player) Computer else Player
}

data class AwariState(
    val beans: List<Int> = (0..13).map {
        if (it in Player.permittedMoves
            || it in Computer.permittedMoves
        ) 3 else 0
    },
    val toMove: ToMove
) {
    val score
        get() =
            beans[Player.home] - beans[Computer.home]

    fun move(n: Int) =
        if (n in toMove.permittedMoves && beans[n] > 0) {
            val beans2 = ArrayList(beans)
            AwariState(beans2, toMove.opponent)
        } else null

    override fun toString() =
        buildString {
            append("    ")
            Computer.permittedMoves.reversed().forEach { append("%04d".format(beans[it])) }
            appendLine()
            append("%04d".format(beans[Computer.home]))
            append("    ".repeat(Computer.permittedMoves.count()))
            append("%04d".format(beans[Player.home]))
            appendLine()
            append("    ")
            Player.permittedMoves.forEach { append("%04d".format(beans[it])) }
        }
}
