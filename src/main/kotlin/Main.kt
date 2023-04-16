import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import domain.Stock
import domain.Tax
import utils.extension.fromValueToString
import utils.extension.transformStringInArray
import java.util.Scanner

class Main {
    companion object {
        fun exec() {
            println("Enter the json:")
            val lines = getLines().joinToString("\n")
            val jsons = lines.transformStringInArray()

            jsons.forEach { json ->
                println(taxes(json.fromValueToString()).fromValueToString())
            }

            println("Finished")
        }

        private fun taxes(json: String): List<Tax> {
            val array = jacksonObjectMapper().readValue(json, Array<Stock>::class.java)
            val capitalGain = CapitalGain()
            return array.map { capitalGain(it) }
        }

        private fun getLines(): List<String> {
            val scanner = Scanner(System.`in`)
            val lines = mutableListOf<String>()

            while (scanner.hasNextLine()) {
                val line = scanner.nextLine().trim()
                if (line.isEmpty()) break
                lines.add(line)
            }

            return lines
        }
    }
}


fun main() {
    Main.exec()
}




