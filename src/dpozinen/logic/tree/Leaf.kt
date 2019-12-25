package dpozinen.logic.tree

/**
 * @author dpozinen
 */
interface Leaf {
	var value: Boolean
	val leaves: List<Leaf>
	fun apply(visited: List<Leaf>, statements: List<String>)
}