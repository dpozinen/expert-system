package dpozinen.io

import dpozinen.logic.leaves.Leaf

object Input {

	var leaves: MutableList<Leaf> = mutableListOf()
	var truths: MutableList<Leaf> = mutableListOf()
	var queries: MutableList<Leaf> = mutableListOf()

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
