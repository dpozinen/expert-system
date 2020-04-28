package dpozinen

import com.xenomachina.argparser.ArgParser
import dpozinen.io.Args
import dpozinen.io.Input
import dpozinen.io.Reader
import dpozinen.logic.Solver
import java.io.File
import java.nio.file.Paths

val args: Args = ArgParser(arrayOf("-t", "-q")).parseInto(::Args)
private val pathToResources = Paths.get("").toAbsolutePath().toString() + "/src/test/resources"

fun getFiles(regex: Regex, path: String = pathToResources): List<String> {
	return File(path).walk().filter { it.name.matches(regex) }
			.flatMap { f -> f.walk().asSequence() }
			.filter { it.isFile }
			.map { it.absolutePath }
			.distinct()
			.toList()
}

fun solve(file: String): Input {
	print("Solving %s%n".format(file))
	val input = Reader(args, file).read()
	solve(input)
	print("----------------------\n\n")
	return input
}

fun solve(input: Input): Input {
	if (input.hasException())
		System.err.println(input.ex!!.message)
	else
		Solver().solve(input).forEach { print(it) }
	return input
}
