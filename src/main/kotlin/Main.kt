import domain.Stock
import domain.Tax
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    println("Hello World!")
}

fun taxes(json: String): List<Tax> {
    val array = Json.decodeFromString<Array<Stock>>(json)
    val capitalGain = CapitalGain()
    return array.map { capitalGain(it) }
}
