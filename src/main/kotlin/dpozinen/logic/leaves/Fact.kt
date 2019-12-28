package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
class Fact(name: String, negate: Boolean = false) : Leaf(name, negate) {

	override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
		if (!visited.contains(this)) {
			visited.add(this)
			if (!value()) {
				statements.add("$name is FALSE")
				var v : Boolean
				for (leaf in leaves) {
					v = leaf.apply(visited, statements)
					if (v) {
						statements.add("$name is now TRUE")
						break // TODO("fix Not values")
					}
				}
			} else
				statements.add("$name is TRUE")
		}
		return value()
	}
}
