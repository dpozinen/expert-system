package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
class Rule(name: String) : Leaf(name) {

	override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
		if (!visited.contains(this)) {
			visited.add(this)
			if (!value()) {
				for (leaf in leaves) {
					val v: Boolean = leaf.apply(visited, statements)
					if (v) {
						statements.add("$this is TRUE")
						value = v
						return value(v)
					}
				}
				statements.add("$this ends up FALSE")
			} else
				statements.add("$this is TRUE")
		}
		return value()
	}

}
