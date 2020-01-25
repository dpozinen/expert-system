package dpozinen.logic.leaves


/**
 * @author dpozinen
 */
// TODO(move value from base)
abstract class Leaf(name: String, private val negate: Boolean = name.startsWith("!"), var value: Boolean = false) {

	val name = if (name.startsWith("!")) name.removePrefix("!") else name
	val leaves = mutableListOf<Leaf>()

	//	TODO("Rule#apply() and Fact#apply() are identical")
	abstract fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean

	fun value(v: Boolean = value) = when {
		negate -> !v
		else -> v
	}

	override fun equals(other: Any?) = other is Leaf && other.name == name && other.negate == negate

	override fun hashCode(): Int = name.hashCode()

	override fun toString(): String = if (negate) "!$name" else name

	fun logVerbose(statements: MutableList<String>) {
		if (false) { // TODO(fix verbose flag)
			val leavesJoin = leaves.joinToString { " and " }
			val statement = "For $this to be TRUE %s must be %b".format(leavesJoin, value(true))
			statements.add(statement)
		}
	}


}
