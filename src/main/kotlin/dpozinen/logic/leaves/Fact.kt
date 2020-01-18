package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
class Fact(name: String, negate: Boolean = false) : Leaf(name, negate) {

	override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
		if (!visited.contains(this)) {
			visited.add(this)
			if (!value()) {
				for (leaf in leaves) {
					val v: Boolean = leaf.apply(visited, statements)
					if (v) {
						statements.add("$name ends up TRUE")
						value = v
						return value(v)
					}
				}
				statements.add("$name is FALSE")
			} else
				statements.add("$name is TRUE")
		}
		return value()
	}
}
