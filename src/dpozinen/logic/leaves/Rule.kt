package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
class Rule(override var value: Boolean, override val leaves: List<Leaf>) : Leaf {

	override fun apply(visited: List<Leaf>, statements: List<String>) {
		TODO("not implemented")
	}
}
