package dpozinen.logic.tree

import com.google.common.base.Stopwatch
import dpozinen.io.Output

/**
 * @author dpozinen
 */

class LogicTree private constructor(private val leaves: List<Leaf>) {

	companion object {
		fun build(leaves: List<Leaf>): LogicTree {
			return LogicTree(leaves)
		}
	}

	@Suppress("UnstableApiUsage")
	fun solveFor(leaf: Leaf): Output {
		val stopwatch = Stopwatch.createStarted()
		val statements = solve(leaf)
		return Output(statements, stopwatch.stop(), leaf)
	}

	private fun solve(leaf: Leaf): List<String> {
		val visited: List<Leaf> = emptyList()
		val statements: List<String> = emptyList()
		leaf.apply(visited, statements)
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}
