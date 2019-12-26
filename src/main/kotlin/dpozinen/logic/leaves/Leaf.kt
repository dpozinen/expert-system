package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
abstract class Leaf(protected val name: String) {
	var value: Boolean = false
	val leaves: MutableList<Leaf> = mutableListOf()
	abstract fun apply(visited: MutableList<Leaf>, statements: MutableList<String>) : Boolean

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
