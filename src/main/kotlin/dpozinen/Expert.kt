package dpozinen

import dpozinen.io.Reader
import dpozinen.logic.Solver

/**
 * @author dpozinen
 */
fun main(args: Array<String>) {
	if (args.isNotEmpty()) {
		val input = Reader(args).read()
		Solver().solve(input).forEach { print(it) }
	} else System.err.println("No file name provided")
}
