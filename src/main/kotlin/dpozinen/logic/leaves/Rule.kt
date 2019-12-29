package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
class Rule(name: String, negate: Boolean = false) : Leaf(name, negate) {

	override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
		if (!visited.contains(this)) {
			visited.add(this)
			if (!value()) {
				statements.add("$name is False")
				for (leaf in leaves) {
					val v: Boolean = leaf.apply(visited, statements)
					if (v) {
						statements.add("$name is TRUE")
						return value(v)
					}
				}
			} else
				statements.add("$name is TRUE")
		}
		return value()
	}

}
