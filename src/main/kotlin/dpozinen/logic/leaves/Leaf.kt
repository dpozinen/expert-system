package dpozinen.logic.leaves

import dpozinen.logic.Symbol


/**
 * @author dpozinen
 */
// TODO(move value from base)
abstract class Leaf(name: String, val negate: Boolean = name.startsWith("!"), var value: Boolean = false) {

	val name = if (name.startsWith("!")) name.removePrefix("!") else name
	val leaves = mutableListOf<Leaf>()

	//	TODO("Rule#apply() and Fact#apply() are identical")
	abstract fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean
	abstract fun formLeafsLog(): String

	open fun value(v: Boolean = value) = when {
		negate -> !v
		else -> v
	}

	override fun equals(other: Any?) = other is Leaf && other.name == name && other.negate == negate

	override fun hashCode(): Int = name.hashCode()

	override fun toString(): String = if (negate) "!$name" else name

	fun logVerbose(statements: MutableList<String>) {
		if (leaves.isNotEmpty()) {
			val msg = formMsg()
			statements.add("For $this to be true $msg should be ${!negate}")
		}
	}

	fun formMsg(joiner: String = ""): String {
		return leaves.map { it.formLeafsLog() }.toList().joinToString(" ${joiner.toLowerCase()} ")
	}

}
