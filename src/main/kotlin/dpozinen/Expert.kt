package dpozinen

import dpozinen.io.Reader
import dpozinen.logic.Solver

/**
 * @author dpozinen
 */
fun main(args: Array<String>) {
	if (args.isNotEmpty()) {
		if (args.contains("-i")) {
			solveInteractive(args)
		} else {
			val input = Reader(args).read()
			Solver().solve(input).forEach { print(it) }
		}
	} else {
		System.err.println("No file name provided and interactive flag wasn't set. ")
		printUsage()
	}
}

fun printUsage() {
	val usage = """
		Either a file name has to be provided as the first argument, or the -i flag has to be set.
		
		-i     --interactive   Allows live, interactive evaluation of input
		
	""".trimIndent()
	print(usage)
}

private fun solveInteractive(args: Array<String>) {
	println("type /h to show help")
	val comparator = Comparator<String> { o1, _ ->
		if (o1.startsWith("?") || o1.startsWith("=")) 1
		else -1
	}.thenComparator { a, b -> a.compareTo(b) }

	val lines = sortedSetOf(comparator)
	while (true) {
		val line = readLine().orEmpty()
		when {
			line.startsWith("/d ") -> lines.removeAll { it.replace(Regex("\\s+"), "") == line.substringAfter("/d ").replace(Regex("\\s+"), "") }
			line == "/s" -> {
				val input = Reader(args).read(lines)
				Solver().solve(input).forEach { print(it) }
			}
			line == "/help" || line == "/h" -> printInteractiveHelp()
			line == "/show" -> println(lines.joinToString("\n"))
			else -> lines.add(line)
		}
	}
}

fun printInteractiveHelp() {
	val interactiveHelp = """
		Any line that is not a command will be considered input to the engine.

		/s - solve
		/d <line> - delete specified line from input (case and space insensitive)
		/help, /h - show help
		/show - shows the current input to the engine

	""".trimIndent()
	print(interactiveHelp)
}
