package dpozinen.io

import dpozinen.logic.leaves.Fact
import dpozinen.logic.leaves.Leaf
import java.io.File

class Reader(private val args: Array<String>) {

	private val fileName: String = args[0]

	fun fillInput() {
		fillFlags()
		fillFromFile()
	}

	private fun fillFlags() {
		if (args.contains("-v")) Input.verbose = true
		if (args.contains("-raq")) Input.rulesAsQueries = true
		if (args.contains("-fn")) Input.fullNames = true
	}

	private fun fillFromFile() {
		val lines = File(fileName).readLines().filter { s -> !s.startsWith("#") }
		for (line in lines)
			when {
				line.startsWith("=") -> fillTruths(line)
				line.startsWith("?") -> fillQueries(line)
				else -> addRule(line)
			}
	}

	private fun fillQueries(line: String) {
		fillWithLeafs(line.removePrefix("?"), Input.queries)
	}

	private fun fillTruths(line: String) {
		fillWithLeafs(line.removePrefix("="), Input.truths)
	}

	private fun addRule(line: String) {
		TODO()
	}

	private fun fillWithLeafs(line: String, target: MutableList<Leaf>) {
		if (Input.rulesAsQueries || Input.fullNames)
			line.split(",")
				.forEach { target.add(Fact(it)) }
		else
			line.map { it.toString() }
				.forEach { target.add(Fact(it)) }
	}
}
