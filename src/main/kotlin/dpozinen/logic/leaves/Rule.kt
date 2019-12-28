package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
class Rule(name: String, negate: Boolean = false) : Leaf(name, negate) {

	override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
		TODO("not implemented")
	}

}
