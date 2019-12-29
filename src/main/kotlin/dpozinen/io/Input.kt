package dpozinen.io

import dpozinen.logic.leaves.Leaf

object Input {

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

}
