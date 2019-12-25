package dpozinen

import dpozinen.io.Reader
import dpozinen.logic.Solver

/**
 * @author dpozinen
 */
fun main(args: Array<String>) {
	if (args.isNotEmpty()) {
		Reader(args[0]).read()
		Solver().solve().forEach { print(it) }
	} else System.err.println("No file name provided")
}
