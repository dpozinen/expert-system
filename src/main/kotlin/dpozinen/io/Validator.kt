package dpozinen.io

import dpozinen.logic.Symbol

/**
 * @author dpozinen
 */

class Validator(private val input: Input) {

	fun checkConclusionOperator(line:String) {
		if (!line.contains("=>")) throw IllegalArgumentException("Missing conclusion operator: $line")
	}

	fun checkBeforeAndAfter(before: String, after:String) {
		if (before.isBlank()) throw IllegalArgumentException("Rule body is empty")
		if (after.isBlank()) throw IllegalArgumentException("Rule conclusion is empty")
	}

	fun checkConclusion(after: String) {
		if (after.contains(Regex("[|^]")))
			throw IllegalArgumentException("Or ans Xor in conclusions are not supported: $after")
		if (!after.contains("+"))
			checkFact(after)
	}

	fun checkFact(fact: String) {
		checkBraces(fact)
		if (input.fullNames) {
			return
		} else {
			val cleanFact = fact.replace(Regex("[()!]"), "")
			if (cleanFact.contains(Regex("\\W+")))
				throw IllegalArgumentException("Fact can't have symbols: [$fact]")
			if (cleanFact.length != 1)
				throw IllegalArgumentException("Invalid fact name: [$fact]")
		}
	}

	private fun checkBraces(fact: String) {
		val braceCountEqual = hasSameOpenCloseBraceCount(fact)
		if (!braceCountEqual)
			throw IllegalArgumentException("Fact brace count doesn't match: $fact")
	}

	private fun checkAdjacentOperators(line: String) {
		val bothAreSymbols: (Pair<Char, Char>) -> Boolean = { p ->
			Symbol.isSymbol(p.first.toString()) && Symbol.isSymbol(p.second.toString())
		}
		val hasAdjacentOperators = line.zipWithNext().filter(bothAreSymbols)
				.firstOrNull { Symbol.isOperator(it.first) && Symbol.isOperator(it.second) } != null

		if (hasAdjacentOperators)
			throw IllegalArgumentException("Found adjacent operators: $line")
	}

	fun isValidSplit(split: List<String>): Boolean {
		if (split.contains(""))
			throw IllegalArgumentException("Bad Rule/Fact provided [%s]".format(split.joinToString(" ")))
		return split.all { hasSameOpenCloseBraceCount(it) }
	}

	fun hasSameOpenCloseBraceCount(s: String) = s.count { c -> c == '(' } - s.count { c -> c == ')' } == 0

	fun preCheckRule(line: String) {
		if (!hasSameOpenCloseBraceCount(line))
			throw IllegalArgumentException("Open and Close brace count is not equal on line: $line")

		if (line.contains(Regex("\\(\\s*\\)")))
			throw IllegalArgumentException("Empty statement found: $line")

		if (line.contains(Regex("\\w!+\\w")))
			throw IllegalArgumentException("Invalid negation found: $line")

		checkAdjacentOperators(line)
	}

}
