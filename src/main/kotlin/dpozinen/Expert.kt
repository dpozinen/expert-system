package dpozinen

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import dpozinen.io.Args
import dpozinen.io.Reader
import dpozinen.logic.Solver

/**
 * @author dpozinen
 */
fun main(args: Array<String>) = mainBody {
	ArgParser(args).parseInto(::Args).run {
		if (args.isNotEmpty()) {
			if (interactive) {
				solveInteractive(this)
			} else {
				for (file in files) {
					val input = Reader(this, file).read()
					Solver().solve(input).forEach { output -> print(output) }
				}
			}
		} else {
			System.err.println("No file name provided and interactive flag wasn't set. ")
		}
	}
}

private fun solveInteractive(args: Args) {
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

private fun printInteractiveHelp() {
	val interactiveHelp = """
		Any line that is not a command will be considered input to the engine.

		/s - solve
		/d <line> - delete specified line from input (case and space insensitive)
		/help, /h - show help
		/show - shows the current input to the engine

	""".trimIndent()
	print(interactiveHelp)
}
