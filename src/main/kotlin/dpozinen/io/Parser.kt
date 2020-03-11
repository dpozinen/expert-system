package dpozinen.io

import dpozinen.logic.Symbol
import dpozinen.logic.leaves.Fact
import dpozinen.logic.leaves.Leaf
import dpozinen.logic.leaves.Rule
import dpozinen.logic.leaves.Sign
import java.lang.Exception
import kotlin.reflect.KFunction1

/**
 * @author dpozinen
 */
class Parser(private val input: Input) {

	private val validator: Validator = Validator()

	fun parseRule(line: String): Leaf {
		validator.preCheckRule(line);
		val leaf: Leaf
		if (line.contains(Regex("[|^+]"))) {
			leaf = Rule(line, line.startsWith("!") && isBraceWrapping(line, ::isNotSplittableByAnyOperator))
			when {
				isSplittableBy(line, Symbol.XOR) -> parse(leaf, line, Symbol.XOR)
				isSplittableBy(line, Symbol.OR) -> parse(leaf, line, Symbol.OR)
				isSplittableBy(line, Symbol.AND) -> parse(leaf, line, Symbol.AND)
				isBraceWrapping(line, ::isNotSplittableByAnyOperator) -> leaf.leaves.add(parseRule(removeBraceWrapping(line)))
				else -> throw IllegalArgumentException("An invalid Rule was provided: [$line]")
			}
		} else {
			leaf = Fact(line)
			if (line.contains("(").or(line.contains(")")) && isBraceWrapping(line, ::isInvalidFact))
				leaf.leaves.add(parseRule(removeBraceWrapping(line)))
			validator.checkFact(line)
		}
		return input.leaves.getOrSave(leaf)
	}

	private fun isInvalidFact(line: String): Boolean {
		return try {
			validator.checkFact(line)
			false
		} catch (ex: Exception) {
			true
		}
	}

	private fun removeBraceWrapping(line: String): String {
		var l = line.removePrefix("!")

		do l = l.removePrefix("(").removeSuffix(")")
		while (l.startsWith("("))

		return l
	}

	private fun isBraceWrapping(line: String, f: KFunction1<String, Boolean>): Boolean {
		if (line.contains("(") || line.contains(")") && validator.hasSameOpenCloseBraceCount(line)) {
			var l = line

			do l = l.removePrefix("!").removePrefix("(").removeSuffix(")")
			while (f.invoke(l) && (l.startsWith("!") || l.startsWith("(")))

			return !f.invoke(l)
		}
		return false
	}

	private fun isNotSplittableByAnyOperator(line: String) = Symbol.operators().none { isSplittableBy(line, it) }

	private fun isSplittableBy(line: String, symbol: Symbol) = line.contains(symbol.symbol) && splitLine(line, symbol).isNotEmpty()

	private fun parse(rule: Rule, line: String, symbol: Symbol) {
		val sign = Sign(symbol)
		rule.leaves.add(sign)

		val split = splitLine(line, symbol)
		val leavesOfRule = split.map { parseRule(it) }.toList()
		sign.leaves.addAll(leavesOfRule)
	}

	private fun splitLine(line: String, symbol: Symbol): List<String> {
		var l = line
		if (hasNoNestedBraces(line))
			l = line.removePrefix("(").removeSuffix(")")

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
