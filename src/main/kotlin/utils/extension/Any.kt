package utils.extension

import kotlin.math.round

fun Any.roundTo(): Double = round(this as Double * 100) / 100