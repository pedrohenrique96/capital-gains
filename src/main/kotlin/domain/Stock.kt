package domain

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Stock(val operation: String,
                 val quantity: Int,
                 @JsonNames("unit-cost")
                 val unitCost: Double)