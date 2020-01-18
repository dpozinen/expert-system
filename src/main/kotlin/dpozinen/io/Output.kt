package dpozinen.io

import com.google.common.base.Stopwatch
import dpozinen.logic.leaves.Leaf
import java.util.concurrent.TimeUnit

@Suppress("UnstableApiUsage")
class Output(
		private val statements: List<String>,
		private val stopwatch: Stopwatch,
		private val target: Leaf
) {

	override fun toString(): String {
		return "Solving $target took %s seconds:%n%s%n"
				.format(stopwatch.elapsed(TimeUnit.SECONDS), statements.joinToString("\n"))
	}
}
