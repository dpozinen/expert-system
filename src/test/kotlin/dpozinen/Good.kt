package dpozinen

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertFalse

class Good {
	@Test
	fun test() {
		val list: List<() -> Unit> = getFiles(Regex("good_files"))
				.map { solve(it) }
				.map { { Assertions.assertFalse(it.hasException()) {"Exception on Input $it was < ${it.ex!!.message} >\n\n"} } }
				.toList()
		assertAll(list)
	}
}
