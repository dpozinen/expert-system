package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
class Fact(name: String) : Leaf(name) {

	override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
		if (!visited.contains(this)) {
			visited.add(this)
			logVerbose(statements)
			if (!visited.containsAll(leaves)) {
				for (leaf in leaves) {
					val v: Boolean = leaf.apply(visited, statements)
					if (v) {
						value = v
					}
				}
			}
			statements.add("$this is ${value()}")
		}
		return value()
	}

	override fun formLeafsLog(): String {
		return this.toString()
	}
}
