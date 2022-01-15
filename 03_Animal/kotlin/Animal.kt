/**
 * ANIMAL
 *
 * Converted from BASIC to Kotlin by John Long (@patimen)
 * Changes by Paul Holt (@chiefpad)
 *
 * Animal is basically a perfect example of a learning binary decision tree. Implement it
 * as such, with the Node either being an Answer if it is a leaf node
 * or a Question if it is a non-terminal node.
 */

fun main() {
    printIntro()
    do {
        val thinking = "ARE YOU THINKING OF AN ANIMAL".ask
        when {
            thinking.startsWith("Y") -> root.guess()
            thinking == "LIST" -> root.list()
        }
    } while (!thinking.startsWith("N"))
}

private fun printIntro() {
    println(
        """
        ${tab(32)}ANIMAL
        ${tab(15)}CREATIVE COMPUTING  MORRISTOWN, NEW JERSEY

        PLAY 'GUESS THE ANIMAL'
        
        THINK OF AN ANIMAL AND THE COMPUTER WILL TRY TO GUESS IT.
        """.trimIndent()
    )
}

fun tab(i: Int) = " ".repeat(i)

private val Char?.validate get() = if (this == 'Y') 'Y' else 'N'
private val Char?.opposite get() = if (this == 'Y') 'N' else 'Y'
private val String.ask: String
    get() {
        print("$this? ")
        return readLine() ?: throw EndOfInput
    }

object EndOfInput : Throwable()

val root = Question(
    text = "DOES IT SWIM",
    mutableMapOf(
        'Y' to Answer("FISH"),
        'N' to Answer("BIRD")
    )
)

interface Node {
    fun guess(): Boolean
    fun list(indent: Int = 0)
}

class Question(val text: String, var children: MutableMap<Char, Node>) : Node {

    override fun guess(): Boolean {
        val answer = text.ask.firstOrNull()
        val nextNode = children[answer]
        if (nextNode?.guess() == false) {
            val newAnimal = "THE ANIMAL YOU WERE THINKING OF WAS A".ask
            val newQuestion =
                "PLEASE TYPE IN A QUESTION THAT WOULD DISTINGUISH A\n$newAnimal FROM A ${(nextNode as? Answer)?.name}".ask
            val newAnswer = "FOR A $newAnimal THE ANSWER WOULD BE ".ask.firstOrNull().validate
            if (answer != null)
                children[answer] = Question(
                    text = newQuestion,
                    children = mutableMapOf(newAnswer to Answer(newAnimal), newAnswer.opposite to nextNode)
                )
        }
        return true
    }

    override fun list(indent: Int) {
        println(this.text)
        this.children.forEach { (t, u) ->
            print("${tab(indent)}$t -> ")
            u.list(indent + 4)
        }
    }
}

data class Answer(val name: String) : Node {
    override fun guess(): Boolean {
        println("IS IT A $name? ")
        return readLine()?.firstOrNull() == 'Y'
    }
    override fun list(indent: Int) {
        println(name)
    }
}