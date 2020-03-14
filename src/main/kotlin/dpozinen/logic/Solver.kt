package dpozinen.logic

import com.google.common.base.Stopwatch
import dpozinen.io.Input
import dpozinen.io.Output
import dpozinen.logic.leaves.Leaf

/**
 * @author dpozinen
 */
class Solver {

	@Suppress("UnstableApiUsage")
	fun solve(input: Input): List<Output> {
		if (input.queries.isEmpty()) {
			if (input.hasException())
				System.err.println(input.ex!!.message!!)
			else
				System.err.println("Could not solve")
			return emptyList()
		}

		return input.queries.map {
			val stopwatch = Stopwatch.createStarted()
			val statements = solveFor(it)
			Output(input, statements, stopwatch.stop(), it)
		}.toList()
	}

	private fun solveFor(leaf: Leaf): List<String> {
		val visited = mutableListOf<Leaf>()
		val statements = mutableListOf<String>()
		leaf.apply(visited, statements)
		return statements
	}

}
