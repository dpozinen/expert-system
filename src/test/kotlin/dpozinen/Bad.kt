package dpozinen

import org.junit.jupiter.api.assertAll
import kotlin.test.assertTrue
import kotlin.test.Test

class Bad {

	@Test
	fun test() {
		val list: List<() -> Unit> = getFiles(Regex("bad_files"))
				.map { solve(it) }
				.map { { assertTrue(it.hasException(), "No Exception on Input $it") } }
				.toList()
		assertAll(list)
	}

}
