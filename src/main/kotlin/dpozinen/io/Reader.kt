package dpozinen.io

import dpozinen.logic.Symbol
import dpozinen.logic.Symbol.*
import dpozinen.logic.leaves.Fact
import dpozinen.logic.leaves.Leaf
import dpozinen.logic.leaves.Rule
import dpozinen.logic.leaves.Sign
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
		val lines = File(fileName).readLines()
				.map { it.replace(Regex("\\s+"), "") }
				.filter { !it.startsWith("#") && it.isNotEmpty() && it.isNotBlank() }
		for (l in lines) {
			val line = l.substringBefore("#")
			when {
				line.startsWith("=") -> fillTruths(line)
				line.startsWith("?") -> fillQueries(line)
				else -> addRule(line)
			}
		}
		makeLeavesTrue()
	}

	private fun makeLeavesTrue() {
		Input.truths.forEach { truth -> Input.leaves.first { it == truth }.value = true }
	}

	private fun fillQueries(line: String) {
		fillWithLeafs(line.removePrefix("?"), Input.queries)
	}

	private fun fillTruths(line: String) {
		fillWithLeafs(line.removePrefix("="), Input.truths)
	}

	private fun fillWithLeafs(line: String, target: MutableList<Leaf>) {
		if (Input.rulesAsQueries || Input.fullNames)
			line.split(",")
					.forEach { addLeafToTarget(target, it) }
		else
			line.map { it.toString() }
					.forEach { addLeafToTarget(target, it) }
	}

	private fun addLeafToTarget(target: MutableList<Leaf>, name: String) {
		val leaf = Input.leaves.firstOrNull { it.name == name }
		if (leaf == null) // TODO("maybe just ignore this one")
			throw IllegalArgumentException("One of the provided targets/truths is invalid")
		else
			target.add(leaf)
	}

	fun addRule(line: String) {
		if (!line.contains("=>")) throw IllegalArgumentException("Missing conclusion operator")
		val delim = if (line.contains(ONLYIF.symbol)) ONLYIF.symbol else IMPLIES.symbol

		val before = line.substringBefore(delim)
		val after = line.substringAfter(delim)

		if (before.isEmpty()) throw IllegalArgumentException("Rule body is empty")
		if (after.isEmpty()) throw IllegalArgumentException("Rule conclusion is empty")

		val body: Leaf = parseRule(before)
		val conclusion: Leaf = parseRule(after)
		conclusion.leaves.add(body)
	}

//	TODO("Add validation checks")
//	TODO("Solve broken refs for (A + B) | (B + C)")
	private fun parseRule(line: String): Leaf {
		val leaf: Leaf
		if (line.contains(Regex("[|^+]"))) {
			leaf = Rule(line, line.startsWith(NOT.symbol)) // TODO("make true only for braces")
			when {
				isSplittableBy(line, XOR) -> parse(leaf, line, XOR)
				isSplittableBy(line, OR) -> parse(leaf, line, OR)
				isSplittableBy(line, AND) -> parse(leaf, line, AND)
			}
		} else {
			leaf = Fact(line, line.startsWith("!"))
		}
		Input.leaves.add(leaf)
		return leaf
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
		if (hasNoNestedParentheses(line))
			l = line.removePrefix("(").removeSuffix(")")

		val indices = l.indices.filter { l[it].toString() == symbol.symbol }.toList()
		for (i in indices) {
			val split = split(l, i)
			if (isValidSplit(split))
				return split
		}
		return emptyList()
	}

	private fun hasNoNestedParentheses(line: String) = line.matches(Regex("^\\([^()]+\\)\$"))

	private fun split(line: String, i: Int) = listOf(line.subSequence(0, i).toString(), line.subSequence(i + 1, line.length).toString())

	private fun isValidSplit(split: List<String>): Boolean {
		return split.all { it.count { c -> c == '(' } - it.count { c -> c == ')' } == 0 }
	}

}
