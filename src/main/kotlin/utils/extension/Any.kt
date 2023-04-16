package utils.extension

import com.fasterxml.jackson.databind.DeserializationFeature
import kotlin.math.round
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

fun Any.roundTo(): Double = round(this as Double * 100) / 100

fun Any.fromValueToString(): String {
    val mapper = getMapper(unwrapRoot = true)

    return mapper.writeValueAsString(this)
}

private fun getMapper(unwrapRoot: Boolean) = ObjectMapper().apply { configure(unwrapRoot) }

private fun ObjectMapper.configure(unwrapRoot: Boolean) {
    registerKotlinModule()
    configure(DeserializationFeature.UNWRAP_ROOT_VALUE, unwrapRoot)
}