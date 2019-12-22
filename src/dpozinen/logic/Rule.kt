package dpozinen.logic

import java.lang.IllegalArgumentException

/**
 * @author dpozinen
 */

class Rule(val facts: List<Fact>, val results: List<Fact>) {

	companion object {
		fun parse(line: String): Rule {
			if (!line.contains("=>")) throw IllegalArgumentException("No <implies> or <only if> found")

			val facts: List<Fact> = emptyList()
			val results: List<Fact> = emptyList()
			val cleanRule = line.replace(Regex("\\s+"), " ").replace("! ", "")

			cleanRule.split(" ").forEach { s ->
				val symbol = when {
					Symbol.isSymbol(s) -> Symbol.createFrom(s)
					else -> Symbol.IS
				}


			}
			return Rule(facts, results)
		}
	}
}
