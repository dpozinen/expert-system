package dpozinen.logic.leaves

import dpozinen.logic.Symbol

/**
 * @author dpozinen
 */
class Sign(name: String, private val symbol: Symbol) : Leaf(name) {

    override fun apply(visited: MutableList<Leaf>, statements: MutableList<String>): Boolean {
        val leafValues = leaves.map { apply(visited, statements) }.toList()
        return applySymbol(symbol, leafValues)
    }

    private fun applySymbol(symbol: Symbol, leafValues: List<Boolean>): Boolean {
        return when(symbol) {
            Symbol.AND -> leafValues.all { it }
            Symbol.OR -> leafValues.any { it }
            Symbol.XOR -> leafValues.singleOrNull { it } != null
            else -> throw UnsupportedOperationException("This should never happen, but an unsupported operator was used")
        }
    }

}
