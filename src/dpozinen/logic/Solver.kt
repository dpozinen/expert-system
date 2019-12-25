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
	fun solve(): List<Output> {
		return Input.queries.map {
			val stopwatch = Stopwatch.createStarted()
			val statements = solveFor(it)
			Output(statements, stopwatch.stop(), it)
		}.toList()
	}

	private fun solveFor(leaf: Leaf): List<String> {
		val visited: List<Leaf> = emptyList()
		val statements: List<String> = emptyList()
		leaf.apply(visited, statements)
		return statements
	}

}
