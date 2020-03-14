package dpozinen.io

import dpozinen.logic.Symbol.IMPLIES
import dpozinen.logic.Symbol.ONLYIF
import dpozinen.logic.leaves.Leaf
import dpozinen.logic.leaves.Rule
import java.io.File
import java.io.IOException

class Reader(private val args: Args, private val fileName: String = "") {
	private val input: Input = Input()

	fun read(lines: Collection<String>): Input {
		try {
			fillFlags()
			val cleanLines = lines.map {
				if (input.fullNames) it.replace(Regex("\\s+"), " ").trim()
				else it.replace(Regex("\\s+"), "")
			}
			.filter { !it.startsWith("#") && it.isNotEmpty() && it.isNotBlank() }

			input.fullInput = cleanLines.joinToString("\n")
			fillLeaves(cleanLines)
		} catch (ex: IllegalArgumentException) {
			input.ex = ex
		}
		return input
	}

	fun read(): Input {
		var lines: List<String>
		try {
			lines = readFile()
		} catch (ex: IOException) {
			input.ex = IllegalStateException("File could not be read: $fileName")
			lines = emptyList()
		}
		return read(lines)
	}

	private fun fillFlags() {
		input.quiet = args.quiet
		input.fullNames = args.fullNames
	}

	private fun readFile() = if (fileName.isNotEmpty()) File(fileName).readLines() else emptyList()

	private fun fillLeaves(lines: List<String>) {
		for (l in lines) {
			val line = l.substringBefore("#")
			when {
				line.startsWith("=") -> fillTruths(line)
				line.startsWith("?") -> fillQueries(line)
//				line.startsWith("|") -> fillAnswers(line)
				else -> addRule(line)
			}
		}
		makeLeavesTrue()
	}

	private fun fillAnswers(line: String) {
		input.answers = line.removePrefix("|").split("|")
							.map { Pair(it.substringBefore("="), it.substringBefore("=").toBoolean()) }
							.toMap()
	}

	private fun makeLeavesTrue() = input.truths.forEach { truth -> input.leaves.first { it == truth }.value = true }

	private fun fillQueries(line: String) = fillWithLeafs(line.removePrefix("?"), input.queries)

	private fun fillTruths(line: String) = fillWithLeafs(line.removePrefix("="), input.truths)

	private fun fillWithLeafs(line: String, target: MutableList<Leaf>) {
		if (input.fullNames)
			line.split(",").forEach { addLeafToTarget(target, it.trim()) }
		else
			line.map { it.toString() }.forEach { addLeafToTarget(target, it) }
	}

	private fun addLeafToTarget(target: MutableList<Leaf>, name: String) {
		val leaf = input.leaves.firstOrNull { it.name == name }
		if (leaf == null)
			throw IllegalArgumentException("The provided target/truth is invalid: $name")
		else
			target.add(leaf)
	}

	private fun addRule(line: String) {
		val validator = Validator(input)
		val parser = Parser(input, validator)

		validator.checkConclusionOperator(line)
		val delim = if (line.contains(ONLYIF.symbol)) ONLYIF.symbol else IMPLIES.symbol

		val before = line.substringBefore(delim)
		val after = line.substringAfter(delim)

		validator.checkBeforeAndAfter(before, after)

		val body: Leaf = parser.parseRule(before)
		validator.checkConclusion(after)
		val conclusion: Leaf = parser.parseRule(after)

		resolveConclusionLinks(conclusion, body, delim)
	}

	private fun resolveConclusionLinks(conclusion: Leaf, body: Leaf, delim: String) {
		if (conclusion is Rule)
			conclusion.leaves.forEach { it.leaves.forEach { c -> c.leaves.add(body) } }
		else
			conclusion.leaves.add(body)
		if (delim == ONLYIF.symbol) {
			if (hasXor(body))
				throw IllegalArgumentException("XORs with only-if conclusions are not permitted: $body")
			if (body is Rule)
				body.leaves.forEach { it.leaves.forEach { c -> c.leaves.add(conclusion) } }
			else
				body.leaves.add(conclusion)
		}
	}

	private fun hasXor(body: Leaf) = body.toString().contains("^")

}
