package dpozinen.io

import dpozinen.logic.leaves.Leaf
import java.lang.Exception

class Input {

	var ex : Exception? = null

	var leaves = mutableListOf<Leaf>()
	var truths = mutableListOf<Leaf>()
	var queries = mutableListOf<Leaf>()

	var fullNames: Boolean = false
	var verbose: Boolean = false
	var rulesAsQueries: Boolean = false

//	fun saveLeaf(leaf: Leaf) = leaves.add(leaf)
//	fun saveTruth(leaf: Leaf) = truths.add(leaf)
//	fun saveQuery(leaf: Leaf) = queries.add(leaf)
// 	TODO("figure out best way to handle openness of Input.class")
//	fun enableVerbose() { verbose = true }
//	fun enableFullNames() { fullNames = true }
//	fun enableRulesAsQueries() { rulesAsQueries = true }

	override fun toString(): String {
		return """
				Found leaves: $leaves
				Found truths: $truths
				Found queries: $queries
			   """.trimIndent()
	}

	fun hasException(): Boolean = ex != null
}
