package dpozinen.io

import com.google.common.base.Stopwatch
import dpozinen.logic.leaves.Leaf

@Suppress("UnstableApiUsage")
class Output(
	private val statements: List<String>,
	private val stopwatch: Stopwatch,
	val target: Leaf


)
