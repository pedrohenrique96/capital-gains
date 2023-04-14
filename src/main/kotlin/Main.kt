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
    val array = Json.decodeFromString<Array<Stock>>(json)
    val main = Main()
    return array.map {main.invoked(it)}
}

@Serializable
data class Stock(val operation: String,
                 val quantity: Int,
                 @JsonNames("unit-cost")
                val unitCost: Double)

data class Tax(val tax: Double)

class Main {
    private var currentWeightedAveragePrice = 0.0
    private var currentDamage = 0.0
    private var currentQuantityCorporateStock = 0

    private var tax = mutableListOf<Tax>()
    fun invoked(stock: Stock): Tax {
        when(stock.operation) {
            "buy" -> {
                setWeightedAveragePrice(weightedAveragePrice(stock.quantity, stock.unitCost))
                setCurrentQuantityCorporateStock(stock.quantity)
                tax.add(Tax(TAX_ZERO))
                return Tax(TAX_ZERO)
            }
            "sell" -> {
                subStocksQuantity(stock.quantity)
                return if (isPriceStockLessThanAveragePrice(stock.unitCost)) {
                    sumDamageAboutSell(stock.unitCost, stock.quantity)
                    tax.add(Tax(TAX_ZERO))
                    Tax(TAX_ZERO)
                } else {
                    if (totalValueIsLessThanMinimumAmountToChargeTax(stock.unitCost, stock.quantity)) {
                        calculationReduceDamage(stock.unitCost, stock.quantity)
                        tax.add(Tax(TAX_ZERO))
                        Tax(TAX_ZERO)
                    } else {
                        val profit = calculateProfit(stock.unitCost, stock.quantity)
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

    private fun totalValueIsLessThanMinimumAmountToChargeTax(value: Double, quantity: Int): Boolean {
        return valorTotalDaOperacaoDeduzidoOPreju(value, quantity) <= MINIMUM_AMOUNT_TO_CHARGE_TAX
    }

    private fun isPriceStockLessThanAveragePrice(value: Double): Boolean {
        return value <= getCurrentWeightedAveragePrice()
    }

    private fun calculationReduceDamage(value: Double, quantity: Int) {
        subDamage(kotlin.math.abs((getCurrentWeightedAveragePrice().minus(value) * quantity)))
    }

    fun getCurrentWeightedAveragePrice(): Double {
        return currentWeightedAveragePrice
    }

    private fun setWeightedAveragePrice(value: Double) {
        currentWeightedAveragePrice = value
    }

    fun getDamage(): Double {
        return currentDamage
    }

    private fun getCurrentQuantityCorporateStock(): Int {
        return currentQuantityCorporateStock
    }

    private fun setCurrentQuantityCorporateStock(quantity: Int) {
        currentQuantityCorporateStock += quantity
    }

    private fun subStocksQuantity(quantity: Int) {
        currentQuantityCorporateStock -= quantity
    }

    private fun sumDamage(value: Double): Double {
        currentDamage += value
        return currentDamage
    }

    private fun subDamage(value: Double) {
        currentDamage = kotlin.math.abs(currentDamage.minus(value))
    }

    fun weightedAveragePrice(quantityStocksBuy: Int, value: Double): Double {
        return ((getCurrentQuantityCorporateStock() * getCurrentWeightedAveragePrice()) + (quantityStocksBuy * value)) /
                (getCurrentQuantityCorporateStock() + quantityStocksBuy)
    }

    fun sumDamageAboutSell(value: Double, quantity: Int): Double {
        return sumDamage(kotlin.math.abs(getCurrentWeightedAveragePrice().minus(value) * quantity))
    }



    fun calculateProfit(stockValue: Double, quantity: Int): Double {
       return (stockValue.minus(getCurrentWeightedAveragePrice()) * quantity).minus(getDamage()) * TAX_PERCENTAGE
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

