package dpozinen.io

import com.google.common.base.Stopwatch
import dpozinen.logic.leaves.Leaf
import java.util.concurrent.TimeUnit

@Suppress("UnstableApiUsage")
class Output(
		private val input: Input,
		private val statements: List<String>,
		private val stopwatch: Stopwatch,
		private val target: Leaf,
		private val results: Map<String, Boolean> = HashMap()
) {

	fun checkResults(answers: Map<String, Boolean>) {
		return results.map {
			"For ${it.key} value is ${it.value} and should be ${answers[it.key]}"
		}.forEach { print(it)}
	}

	override fun toString(): String {
		return if (input.quite) {
			if (statements.isNotEmpty())
				statements.last() + "\n"
			else
				"Could not find solution"
		} else {
			"""Solving $target took ${stopwatch.elapsed(TimeUnit.MICROSECONDS)} microseconds:
				|${statements.joinToString("\n")}
			""".trimMargin()
		}
	}
}
