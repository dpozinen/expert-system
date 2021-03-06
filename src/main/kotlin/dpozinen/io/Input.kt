package dpozinen.io

import dpozinen.logic.leaves.Leaf

class Input {

	var answers: Map<String, Boolean> = HashMap()

	var fullInput: String = ""
	var ex: Exception? = null

	var leaves = mutableListOf<Leaf>()
	var truths = mutableListOf<Leaf>()
	var queries = mutableListOf<Leaf>()

	var fullNames: Boolean = false
	var quiet: Boolean = false

	override fun toString(): String = "\n" + fullInput + "\n"

	fun hasException(): Boolean = ex != null
}
