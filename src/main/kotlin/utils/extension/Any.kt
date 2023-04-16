package utils.extension

import com.fasterxml.jackson.databind.DeserializationFeature
import kotlin.math.round
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import domain.Stock

fun Any.roundTo(): Double = round(this as Double * 100) / 100

fun Any.fromValueToString(): String {
    val mapper = getMapper(unwrapRoot = true)

    return mapper.writeValueAsString(this)
}

fun Any.transformStringInArray(): MutableList<List<Stock>> {
    val arrays = mutableListOf<List<Stock>>()

    val input = this.toString()

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

private fun getMapper(unwrapRoot: Boolean) = ObjectMapper().apply { configure(unwrapRoot) }

private fun ObjectMapper.configure(unwrapRoot: Boolean) {
    registerKotlinModule()
    configure(DeserializationFeature.UNWRAP_ROOT_VALUE, unwrapRoot)
}