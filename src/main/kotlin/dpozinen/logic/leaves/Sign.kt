package dpozinen.logic.leaves

import dpozinen.logic.Symbol

/**
 * @author dpozinen
 */
class Sign(private val symbol: Symbol, name: String = symbol.name) : Leaf(name) {

	override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
		val leafValues = mutableListOf<Boolean>()
		for (leaf in leaves) {
			val res = leaf.apply(visited, statements)
			leafValues.add(res)
			if (symbol == Symbol.OR && res) return true
		}
		return applySymbol(symbol, leafValues)
	}

	private fun applySymbol(symbol: Symbol, leafValues: MutableList<Boolean>): Boolean {
		return when (symbol) {
			Symbol.AND -> leafValues.all { it }
			Symbol.OR -> leafValues.any { it }
			Symbol.XOR -> leafValues.singleOrNull { it } != null
			else -> throw UnsupportedOperationException("This should never happen, but an unsupported operator was used")
		}
	}

	override fun formLeafsLog(): String {
		return super.formMsg(toString())
	}

	override fun toString(): String {
		return when (this.symbol) {
			Symbol.AND -> "and"
			Symbol.XOR -> "and"
			Symbol.OR -> "or"
			else -> ""
		}
	}
}
