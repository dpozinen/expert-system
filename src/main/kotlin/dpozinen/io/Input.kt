package dpozinen.io

import dpozinen.logic.leaves.Leaf

class Input {

	var fullInput: String = ""
	var ex: Exception? = null

	var leaves = mutableListOf<Leaf>()
	var truths = mutableListOf<Leaf>()
	var queries = mutableListOf<Leaf>()

	var fullNames: Boolean = false
	var verbose: Boolean = false
	var rulesAsQueries: Boolean = false

	override fun toString(): String = fullInput

	fun hasException(): Boolean = ex != null
}
