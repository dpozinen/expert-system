package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
class Rule(name: String, negate: Boolean, private var isUndefined: Boolean = true) : Leaf(name, negate) {

	override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
		if (!visited.contains(this)) {
			visited.add(this)
			logVerbose(statements)
			if (!visited.containsAll(leaves[0].leaves)) {
				for (leaf in leaves) {
					val v: Boolean = leaf.apply(visited, statements)
					if (super.value(v)) {
						value = v
						isUndefined = false
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
