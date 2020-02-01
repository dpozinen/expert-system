package dpozinen.io

import dpozinen.logic.Symbol
import dpozinen.logic.leaves.Fact
import dpozinen.logic.leaves.Leaf
import dpozinen.logic.leaves.Rule
import dpozinen.logic.leaves.Sign

/**
 * @author dpozinen
 */
class Parser(private val input: Input) {

	private val validator: Validator = Validator()

	fun parseRule(line: String): Leaf {
		val leaf: Leaf
		if (line.contains(Regex("[|^+]"))) {
			leaf = Rule(line, line.startsWith("!") && isBraceWrapping(line)) // TODO("make true only for braces")
			when {
				isSplittableBy(line, Symbol.XOR) -> parse(leaf, line, Symbol.XOR)
				isSplittableBy(line, Symbol.OR) -> parse(leaf, line, Symbol.OR)
				isSplittableBy(line, Symbol.AND) -> parse(leaf, line, Symbol.AND)
				isBraceWrapping(line) -> parse(leaf, line)
				else -> throw IllegalArgumentException("An invalid Rule was provided: [$line]")
			}
		} else {
			leaf = Fact(line)
			if (isBraceWrapping(line))
				parse(leaf, line)
			validator.checkFact(line)
		}
		return input.leaves.getOrSave(leaf)
	}

	private fun isBraceWrapping(line: String): Boolean {
		if (line.contains(Regex("[|^+]"))) {
			var l = line.removePrefix("!") // TODO(can't just remove ! straight off)
			if (l.startsWith("(") && isNotSplittableByOperator(l)) {
				while (isNotSplittableByOperator(l) && l.removePrefix("!").startsWith("("))
					l = l.removePrefix("!").removePrefix("(").removeSuffix(")")
				return !isNotSplittableByOperator(l)
			}
			return false
		} else {
			var l = line.removePrefix("!")
			if (l.startsWith("(")) {
				while (l.removePrefix("!").startsWith("("))
					l = l.removePrefix("!").removePrefix("(").removeSuffix(")")
				try {
					validator.checkFact(l)
				} catch (e: IllegalArgumentException) {
					return false
				}
				return true
			}
			return false
		}
	}

	private fun isNotSplittableByOperator(l: String) = Symbol.operators().none { isSplittableBy(l, it) }

	private fun isSplittableBy(line: String, symbol: Symbol) = line.contains(symbol.symbol) && splitLine(line, symbol).isNotEmpty()

	private fun parse(rule: Fact, line: String): Leaf {
		val inside = line.removePrefix("!").removePrefix("(").removeSuffix(")")
		rule.leaves.add(parseRule(inside))
		return rule
	}

	private fun parse(rule: Leaf, line: String): Leaf {
		if (isNotSplittableByOperator(line)) {

//			val negate = line.startsWith("!")
			val inside = line.removePrefix("!").removePrefix("(").removeSuffix(")")
//			inside = when {negate -> "!$inside" else -> inside}
			rule.leaves.add(parseRule(inside))
		} else when {
			isSplittableBy(line, Symbol.XOR) -> parse(rule, line, Symbol.XOR)
			isSplittableBy(line, Symbol.OR) -> parse(rule, line, Symbol.OR)
			isSplittableBy(line, Symbol.AND) -> parse(rule, line, Symbol.AND)
		}
		return rule
	}

	private fun parse(rule: Leaf, line: String, symbol: Symbol) {
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
