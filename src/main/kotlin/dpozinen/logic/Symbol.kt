package dpozinen.logic

/**
 * @author dpozinen
 */
enum class Symbol(val symbol: String) {
	IS("."),
	NOT("!"),
	AND("+") {
		override fun toString() = "and"
	},
	OR("|") {
		override fun toString() = "or"
	},
	XOR("^") {
		override fun toString() = "xor"
	},
	IMPLIES("=>"),
	ONLYIF("<=>");

	companion object {
		fun isSymbol(s: String): Boolean = values().any { symbol -> symbol.symbol == s }
		fun createFrom(s: String): Symbol = values().find { symbol -> symbol.symbol == s }!!
		fun operators() = listOf(AND, OR, XOR)
		fun isOperator(s: String) = operators().any { it.symbol == s }
		fun isOperator(c: Char) = operators().any { it.symbol == c.toString() }
	}
}
