package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
abstract class Leaf(val name: String, var value: Boolean = false, private val negate: Boolean = false) {
	val leaves = mutableListOf<Leaf>()

//	TODO("Rule#apply() and Fact#apply() are identical")
	abstract fun apply(visited: MutableList<Leaf>, statements: MutableList<String>) : Boolean

	fun value(v: Boolean = value) : Boolean = when {
		negate -> !v
		else -> v
	}

	override fun equals(other: Any?): Boolean {
		return other is Leaf && other.name == name
	}

	override fun toString(): String {
		return name
	}

	override fun hashCode(): Int {
		return name.hashCode()
	}



}
