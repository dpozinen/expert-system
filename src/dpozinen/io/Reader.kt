package dpozinen.io

import java.io.File

class Reader(fileName: String) {

    private val filename: String = fileName

    fun read() {
        val lines = File(filename).readLines().filter { s -> !s.startsWith("#") }

    }
}
