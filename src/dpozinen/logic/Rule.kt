package dpozinen.logic

/**
 * @author dpozinen
 */

class Rule {

	companion object {
		fun parse(line: String): Rule {
			val cleanRule = line.replace(Regex("\\s+"), " ").replace("! ", "")
			cleanRule.split(" ").forEach { s ->
				if (Symbol.isSymbol(s)) {

				}
			}
			return Rule()
		}


	}
}
