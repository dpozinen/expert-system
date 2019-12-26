package dpozinen.logic

/**
 * @author dpozinen
 */
enum class Symbol(private val symbol: String) {
	IS("."),
	NOT("!"),
	AND("+"),
	OR("|"),
	XOR("^"),
	IMPLIES("=>"),
	ONLYIF("<=>");

	companion object {
		fun isSymbol(s: String): Boolean = values().any { symbol -> symbol.symbol == s }
		fun createFrom(s: String): Symbol = values().find { symbol -> symbol.symbol == s }!!

	}
}
