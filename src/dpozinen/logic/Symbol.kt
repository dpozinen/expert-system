package dpozinen.logic

/**
 * @author dpozinen
 */
enum class Symbol(val symbol: String) {
    NOT("!"),
    AND("+"),
    OR("|"),
    IMPLIES("=>"),
    ONLYIF("<=>")
}
