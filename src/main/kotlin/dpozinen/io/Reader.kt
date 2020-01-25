package dpozinen.io

import dpozinen.logic.Symbol
import dpozinen.logic.Symbol.*
import dpozinen.logic.leaves.Fact
import dpozinen.logic.leaves.Leaf
import dpozinen.logic.leaves.Rule
import dpozinen.logic.leaves.Sign
import java.io.File

// TODO(refactor validation)
class Reader(private val args: Array<String>) {

	private val fileName: String = if (args.isNotEmpty()) args[0] else ""
	private val input: Input = Input()
	private val validator: Validator = Validator()

	fun read(lines: List<String>): Input {
		try {
			val cleanLines = lines.map { it.replace(Regex("\\s+"), "") }
					.filter { !it.startsWith("#") && it.isNotEmpty() && it.isNotBlank() }
			input.fullInput = cleanLines.joinToString(" ")
			fillFlags()
			fillLeaves(cleanLines)
		} catch (ex: IllegalArgumentException) {
			input.ex = ex
		}
		return input
	}

	fun read(): Input {
		val lines = readFile()
		return read(lines)
	}

	private fun fillFlags() {
		if (args.contains("-v")) input.verbose = true
		if (args.contains("-raq")) input.rulesAsQueries = true
		if (args.contains("-fn")) input.fullNames = true
	}

	private fun readFile() = if (fileName.isNotEmpty()) File(fileName).readLines() else emptyList()

	private fun fillLeaves(lines: List<String>) {
		for (l in lines) {
			val line = l.substringBefore("#")
			when {
				line.startsWith("=") -> fillTruths(line)
				line.startsWith("?") -> fillQueries(line)
				line.startsWith("|") -> fillAnswers(line)
				else -> addRule(line)
			}
		}
		makeLeavesTrue()
	}

	private fun fillAnswers(line: String) {
		input.answers =
				line.removePrefix("|").split("|")
						.map { Pair(it.substringBefore("="), it.substringBefore("=").toBoolean()) }
						.toMap()
	}

	private fun makeLeavesTrue() = input.truths.forEach { truth -> input.leaves.first { it == truth }.value = true }

	private fun fillQueries(line: String) = fillWithLeafs(line.removePrefix("?"), input.queries)

	private fun fillTruths(line: String) = fillWithLeafs(line.removePrefix("="), input.truths)

	private fun fillWithLeafs(line: String, target: MutableList<Leaf>) {
		if (input.rulesAsQueries || input.fullNames)
			line.split(",").forEach { addLeafToTarget(target, it) }
		else
			line.map { it.toString() }.forEach { addLeafToTarget(target, it) }
	}

	private fun addLeafToTarget(target: MutableList<Leaf>, name: String) {
		val leaf = input.leaves.firstOrNull { it.toString() == name }
		if (leaf == null) // TODO("maybe just ignore this one")
			throw IllegalArgumentException("One of the provided targets/truths is invalid")
		else
			target.add(leaf)
	}

	private fun addRule(line: String) {
		validator.checkConclusionOperator(line)
		val delim = if (line.contains(ONLYIF.symbol)) ONLYIF.symbol else IMPLIES.symbol

		val before = line.substringBefore(delim)
		val after = line.substringAfter(delim)

		validator.checkBeforeAndAfter(before, after)

		val body: Leaf = parseRule(before)
		validator.checkConclusion(after)
		val conclusion: Leaf = parseRule(after)
		conclusion.leaves.add(body)
	}

	private fun parseRule(line: String): Leaf {
		val leaf: Leaf
		if (line.contains(Regex("[|^+]"))) {
			leaf = Rule(line, line.startsWith(NOT.symbol)) // TODO("make true only for braces")
			when {
				isSplittableBy(line, XOR) -> parse(leaf, line, XOR)
				isSplittableBy(line, OR) -> parse(leaf, line, OR)
				isSplittableBy(line, AND) -> parse(leaf, line, AND)
				else -> throw IllegalArgumentException("An invalid Rule was provided: [$line]")
			}
		} else {
			validator.checkFact(line)
			leaf = Fact(line, line.startsWith("!"))
		}
		return input.leaves.getOrSave(leaf)
	}

	private fun isSplittableBy(line: String, symbol: Symbol) = line.contains(symbol.symbol) && splitLine(line, symbol).isNotEmpty()

	private fun parse(rule: Rule, line: String, symbol: Symbol) {
		val sign = Sign(symbol)
		rule.leaves.add(sign)

		val split = splitLine(line, symbol)
		sign.leaves.addAll(split.map { parseRule(it) }.toList())
	}

	private fun splitLine(line: String, symbol: Symbol): List<String> {
		var l = line
		if (hasNoNestedBraces(line))
			l = line.removePrefix("(").removeSuffix(")")
		validator.checkAdjacentOperators(line)

		val indices = l.indices.filter { l[it].toString() == symbol.symbol }.toList()
		for (i in indices) {
			val split = split(l, i)
			if (validator.isValidSplit(split)) return split
		}
		return emptyList()
	}

	private fun hasNoNestedBraces(line: String) = line.matches(Regex("^\\([^()]+\\)\$"))

	private fun split(line: String, i: Int) = listOf(line.subSequence(0, i).toString(), line.subSequence(i + 1, line.length).toString())

}

private fun <E> MutableList<E>.getOrSave(leaf: E): E {
	return if (this.contains(leaf)) {
		this.first { it == leaf }
	} else {
		add(leaf)
		leaf
	}
}
