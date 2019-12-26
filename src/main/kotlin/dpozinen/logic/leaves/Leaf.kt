package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
abstract class Leaf(protected val name: String, private var value: Boolean = false, private val negate: Boolean = false) {
	val leaves: MutableList<Leaf> = mutableListOf()

	abstract fun apply(visited: MutableList<Leaf>, statements: MutableList<String>) : Boolean

	fun value() : Boolean = when {
		negate -> !value
		else -> value
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
