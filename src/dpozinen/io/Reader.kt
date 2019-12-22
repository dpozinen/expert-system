package dpozinen.io

import dpozinen.logic.Rule
import java.io.File

class Reader(private val fileName: String) {

    fun read() {
        Input.rules = File(fileName).readLines()
            .filter { s -> !s.startsWith("#") }
            .map { s -> s.replace(Regex("\\s+"), " ").replace(Regex("! "), "") }
            .map(Rule.Companion::parse)
            .toList()

    }

}
