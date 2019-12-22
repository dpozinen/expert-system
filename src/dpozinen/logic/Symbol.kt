package dpozinen.logic

/**
 * @author dpozinen
 */
enum class Symbol(private val symbol: String) {
	NOT("!"),
	AND("+"),
	OR("|"),
	IMPLIES("=>"),
	ONLYIF("<=>");

	companion object {
		fun isSymbol(s: String): Boolean = values().any { symbol -> symbol.symbol == s }
	}
}
