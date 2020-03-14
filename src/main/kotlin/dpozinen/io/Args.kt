package dpozinen.io

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.SystemExitException

/**
 * @author dpozinen
 */

class Args(parser: ArgParser) {
	val interactive by parser.flagging(
			"-i", "--interactive",
			help = "Enable interactive mode, where input is taken from stdin. Use /help or /h to show help in this mode"
	)

	val files by parser.adding(
			"-f", "--file",
			help = "File to read from"
	).addValidator { if (value.isEmpty() && !interactive) throw SystemExitException("Interactive flag not set and no files provided", 0) }

	val quiet by parser.flagging(
			"-q", "--quiet",
			help = "Enable quiet mode, only the result will be shown without steps"
	)

	val fullNames by parser.flagging(
			"-e", "--extendedNames",
			help = """Enables full names as fact.
					| When this is enabled facts can be provided as sentences in single quotes:
					| 'is green' + 'is juicy' => 'is watermelon'
					| """.trimMargin()
	)

}
