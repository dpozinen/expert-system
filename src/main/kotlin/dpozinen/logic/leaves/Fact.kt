package dpozinen.logic.leaves

/**
 * @author dpozinen
 */
class Fact(name: String) : Leaf(name) {

	override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
		if (!visited.contains(this)) {
			visited.add(this)
			if (isUndefined) {
				logVerbose(statements)
				for (leaf in leaves) {
					val v: Boolean = leaf.apply(visited, statements)
					isUndefined = false
					if (super.value(v)) {
						value = v
						break
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
