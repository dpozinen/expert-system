package dpozinen.io

import dpozinen.logic.Symbol

/**
 * @author dpozinen
 */

class Validator {

	fun checkConclusionOperator(line:String) {
		if (!line.contains("=>")) throw IllegalArgumentException("Missing conclusion operator in <$line>")
	}

	fun checkBeforeAndAfter(before: String, after:String) {
		if (before.isBlank()) throw IllegalArgumentException("Rule body is empty")
		if (after.isBlank()) throw IllegalArgumentException("Rule conclusion is empty")
	}

	fun checkConclusion(after: String) {
		if (after.contains(Regex("[|^]")))
			throw IllegalArgumentException("Or ans Xor in conclusions are not supported")
		if (!after.contains("+"))
			checkFact(after)
	}

	fun checkFact(fact: String) {
//		if (fact.count { it == '!' } > 1)
//			throw IllegalArgumentException("Fact can't have two negate signs: [$fact]")
		checkBraces(fact)
		if (fact.replace(Regex("[()!]"), "").contains(Regex("\\W+")))
			throw IllegalArgumentException("Fact can't have symbols: [$fact]")
	}

	private fun checkBraces(fact: String) {
		val braceCountEqual = fact.count { it == ')' } == fact.count { it == '(' }
		if (!braceCountEqual)
			throw IllegalArgumentException("Fact brace count doesn't match: [$fact]")
		if (fact.contains(Regex("[()]")) && !fact.matches(Regex("!?\\(+\\w\\)+")))
			throw IllegalArgumentException("Fact brace position is not valid: [$fact]")
	}

	fun checkAdjacentOperators(line: String) {
		val bothAreSymbols: (Pair<Char, Char>) -> Boolean = { p ->
			Symbol.isSymbol(p.first.toString()) && Symbol.isSymbol(p.second.toString())
		}
		val hasAdjacentOperators =  line.zipWithNext().filter(bothAreSymbols).firstOrNull { it.first == it.second } != null

		if (hasAdjacentOperators)
			throw IllegalArgumentException("Found adjacent operators in <$line>")
	}

	fun isValidSplit(split: List<String>): Boolean {
		if (split.contains(""))
			throw IllegalArgumentException("Bad Rule/Fact provided [%s]".format(split.joinToString(" ")))
		return split.all { it.count { c -> c == '(' } - it.count { c -> c == ')' } == 0 }
	}

}
