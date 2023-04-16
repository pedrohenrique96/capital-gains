package domain

import com.fasterxml.jackson.annotation.JsonProperty

data class Stock(
     val operation: String,
     val quantity: Int,
     @JsonProperty("unit-cost")
     val unitCost: Double
)