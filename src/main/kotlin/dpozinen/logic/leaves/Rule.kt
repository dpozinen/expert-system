package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
class Rule(name: String, negate: Boolean) : Leaf(name, negate) {

	override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
		if (!visited.contains(this)) {
			visited.add(this)
			if (isUndefined) {
				logVerbose(statements)
				for (leaf in leaves) {
					val v: Boolean = leaf.apply(visited, statements)
					value = v
					isUndefined = false
					if (super.value(v)) {
						value = v
						break
					}
				}
			}
			statements.add("$this is ${if (isUndefined) "is undefined, defaulting to false" else value().toString()}")
		}
		return value()
	}

	override fun formLeafsLog(): String {
		return this.toString()
	}
}
