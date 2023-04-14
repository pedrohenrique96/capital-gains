import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

fun main(args: Array<String>) {
    println("Hello World!")
}

const val TAX_PERCENTAGE = 0.20
const val TAX_ZERO = 0.0
const val MINIMUM_AMOUNT_TO_CHARGE_TAX = 20000.0


fun called(json: String): List<Tax> {
    val array = Json.decodeFromString<Array<CorporateStock>>(json)
    val main = Main()

    return array.map { acao ->
        main.juntarTodo(acao)
    }
}

@Serializable
data class CorporateStock(val operation: String,
                          val quantity: Int,
                          @JsonNames("unit-cost")
                val unitCost: Double)

data class Tax(val tax: Double)

class Main {
    private var currentWeightedAveragePrice = 0.0
    private var currentDamage = 0.0
    private var currentQuantityCorporateStock = 0

    private var tax = mutableListOf<Tax>()
    fun juntarTodo(corporateStock: CorporateStock): Tax {
        when(corporateStock.operation) {
            "buy" -> {
                currentWeightedAveragePrice = weightedAveragePrice(corporateStock.quantity, corporateStock.unitCost)
                currentQuantityCorporateStock += corporateStock.quantity
                tax.add(Tax(TAX_ZERO))
                return Tax(TAX_ZERO)
            }
            "sell" -> {
                subStocksQuantity(corporateStock.quantity)
                return if (currentWeightedAveragePrice >= corporateStock.unitCost) {
                    sumDamageAboutSell(corporateStock.unitCost, corporateStock.quantity)
                    tax.add(Tax(TAX_ZERO))
                    Tax(TAX_ZERO)
                } else {
                    val valorTotal = valorTotalDaOperacaoDeduzidoOPreju(corporateStock.unitCost, corporateStock.quantity)
                    if (valorTotal <= MINIMUM_AMOUNT_TO_CHARGE_TAX) {
                        subDamage(kotlin.math.abs((getCurrentWeightedAveragePrice().minus(corporateStock.unitCost) * corporateStock.quantity)))
                        tax.add(Tax(TAX_ZERO))
                        Tax(TAX_ZERO)
                    } else {
                        val profit = calculateProfit(corporateStock.unitCost, corporateStock.quantity) * TAX_PERCENTAGE
                        tax.add(Tax(profit))
                        Tax(profit)
                    }
                }
            }
            else -> {
                tax.add(Tax(TAX_ZERO))
                return Tax(TAX_ZERO)
            }
        }
    }

    fun getCurrentWeightedAveragePrice(): Double {
        return currentWeightedAveragePrice
    }

    fun getDamage(): Double {
        return currentDamage
    }

    private fun getCurrentQuantityCorporateStock(): Int {
        return currentQuantityCorporateStock
    }

    private fun subStocksQuantity(quantity: Int) {
        currentQuantityCorporateStock -= quantity
    }

    fun weightedAveragePrice(quantityStocksBuy: Int, value: Double): Double {
        return ((getCurrentQuantityCorporateStock() * getCurrentWeightedAveragePrice()) + (quantityStocksBuy * value)) /
                (getCurrentQuantityCorporateStock() + quantityStocksBuy)
    }

    fun sumDamageAboutSell(value: Double, quantity: Int): Double {
        return sumDamage(kotlin.math.abs(getCurrentWeightedAveragePrice().minus(value) * quantity))
    }

    private fun sumDamage(value: Double): Double {
        currentDamage += value
        return currentDamage
    }

    private fun subDamage(value: Double) {
        currentDamage = kotlin.math.abs(currentDamage.minus(value))
    }

    fun calculateProfit(stockValue: Double, quantity: Int): Double {
       return (stockValue.minus(getCurrentWeightedAveragePrice()) * quantity).minus(getDamage())
    }

    fun calcularDiferencaPrejuizo(valorTotal: Double): Double {
        return kotlin.math.abs(valorTotal.minus(getDamage()))
    }

    fun valorTotalDaOperacaoDeduzidoOPreju(custoUnitárioDaAção: Double, quantidade: Int): Double {
        val valorTotal = custoUnitárioDaAção * quantidade

        if (valorTotal <= getDamage()) {
            return 0.0
        }

        return calcularDiferencaPrejuizo(valorTotal)
    }
}

