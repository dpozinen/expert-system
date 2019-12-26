package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
class Fact(name: String) : Leaf(name) {

	override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
		if (!visited.contains(this)) {
			visited.add(this)
			if (!value) {
				statements.add("$name is FALSE")
				for (leaf in leaves) {
					value = leaf.apply(visited, statements)
					if (value) {
						statements.add("$name is now TRUE")
						break
					}
				}
			} else
				statements.add("$name is TRUE")
		}
		return value
	}
}
