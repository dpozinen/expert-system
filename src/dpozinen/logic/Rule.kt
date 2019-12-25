package dpozinen.logic

import dpozinen.logic.tree.Leaf

/**
 * @author dpozinen
 */
class Rule(override var value: Boolean, override val leaves: List<Leaf>) : Leaf {

	override fun apply(visited: List<Leaf>, statements: List<String>) {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}
