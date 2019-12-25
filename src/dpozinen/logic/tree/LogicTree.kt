package dpozinen.logic.tree

/**
 * @author dpozinen
 */

class LogicTree private constructor(private val leaves: List<Leaf>) {

	companion object {
		fun build(leaves: List<Leaf>):LogicTree {

			return LogicTree(leaves)
		}
	}
}
