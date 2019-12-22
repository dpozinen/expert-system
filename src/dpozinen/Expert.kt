package dpozinen

import dpozinen.io.Reader
import dpozinen.logic.Solver
import java.lang.IllegalArgumentException

/**
 * @author dpozinen
 */
fun main(args:Array<String>) {
    if (args.isNotEmpty()) {
        Reader(args[0]).read()
        Solver().solve()
    } else throw IllegalArgumentException("No file name provided")
}
