package dpozinen.io

import com.google.common.base.Stopwatch
import dpozinen.logic.leaves.Leaf
import java.util.concurrent.TimeUnit

@Suppress("UnstableApiUsage")
class Output(
		private val statements: List<String>,
		private val stopwatch: Stopwatch,
		val results: Map<String, Boolean>,
		private val target: Leaf
) {

	fun checkResults(answers: Map<String, Boolean>) {
		return results.map {
			"For ${it.key} value is ${it.value} and should be ${answers[it.key]}"
		}.forEach { print(it)}
	}

	override fun toString(): String {
		return "Solving $target took %s seconds:%n%s%n"
				.format(stopwatch.elapsed(TimeUnit.SECONDS), statements.joinToString("\n"))
	}
}
