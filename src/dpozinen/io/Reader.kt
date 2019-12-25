package dpozinen.io

import java.io.File

class Reader(private val args: Array<String>) {

	private val fileName: String = args[0]

	fun fillInput() {
		fillFromFile()
		fillFlags()
	}

	private fun fillFlags() {
		TODO()
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

	private fun addRule(line: String) {
		TODO()
	}

	private fun fillQueries(line: String) {
		TODO()
	}

	private fun fillTruths(line: String) {
		TODO()
	}

}
