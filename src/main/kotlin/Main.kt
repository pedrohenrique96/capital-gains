import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import domain.Stock
import domain.Tax
import utils.extension.fromValueToString
import java.util.Scanner

fun main() {
    println("Enter the json:")
    val lines = getLines().joinToString("\n")
    val arrayOfJson = transformStringOfJsonInArray(lines)

    arrayOfJson.forEach { json ->
        println(taxes(json.fromValueToString()).fromValueToString())
    }

    println("Finished")
}

fun taxes(json: String): List<Tax> {
    val array = jacksonObjectMapper().readValue(json, Array<Stock>::class.java)
    val capitalGain = CapitalGain()
    return array.map { capitalGain(it) }
}

private fun getLines(): List<String> {
    val scanner = Scanner(System.`in`)
    val lines = mutableListOf<String>()

    while (true) {
        val line = scanner.nextLine().trim()
        if (line.isEmpty()) break
        lines.add(line)
    }

    return lines
}

private fun transformStringOfJsonInArray(input: String): MutableList<List<Stock>> {
    val arrays = mutableListOf<List<Stock>>()

    var startIndex = input.indexOf('[')
    while (startIndex != -1) {
        val endIndex = input.indexOf(']', startIndex)
        val arrayStr = input.substring(startIndex, endIndex + 1)
        val array = jacksonObjectMapper().readValue(arrayStr, List::class.java) as List<Stock>
        arrays.add(array)
        startIndex = input.indexOf('[', endIndex)
    }

    return arrays
}
