import domain.Stock
import domain.Tax
import utils.extension.roundTo

const val TAX_PERCENTAGE = 0.20
const val TAX_ZERO = 0.00
const val MINIMUM_AMOUNT_TO_CHARGE_TAX = 20000.00
const val OPERATION_IS_NOT_VALID = -1.0

class CapitalGain {
    private var currentWeightedAveragePrice = 0.0
    private var currentDamage = 0.0
    private var currentQuantityCorporateStock = 0

    private var tax = mutableListOf<Tax>()

    operator fun invoke(stock: Stock): Tax {
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
                return Tax(OPERATION_IS_NOT_VALID)
            }
        }
    }

    private fun totalValueIsLessThanMinimumAmountToChargeTax(value: Double, quantity: Int) =
        totalAmountLessDamage(value, quantity) <= MINIMUM_AMOUNT_TO_CHARGE_TAX

    private fun isPriceStockLessThanAveragePrice(value: Double) = value <= getCurrentWeightedAveragePrice()

    private fun calculationReduceDamage(value: Double, quantity: Int) =
        subDamage(kotlin.math.abs((getCurrentWeightedAveragePrice().minus(value) * quantity)))

    fun getCurrentWeightedAveragePrice() = currentWeightedAveragePrice

    private fun setWeightedAveragePrice(value: Double) { currentWeightedAveragePrice = value }

    fun getDamage() =  currentDamage

    private fun getCurrentQuantityCorporateStock() = currentQuantityCorporateStock

    private fun setCurrentQuantityCorporateStock(quantity: Int) { currentQuantityCorporateStock += quantity }

    private fun subStocksQuantity(quantity: Int) { currentQuantityCorporateStock -= quantity }

    private fun sumDamage(value: Double) { currentDamage += value }

    private fun subDamage(value: Double) { currentDamage = kotlin.math.abs(currentDamage.minus(value)) }

    fun weightedAveragePrice(quantityStocksBuy: Int, value: Double): Double {
        val multiplyCurrentStockAndWeightedAveragePrice = getCurrentQuantityCorporateStock() * getCurrentWeightedAveragePrice()
        val multiplyQuantityStockBuyAndValue = quantityStocksBuy * value
        val sumCurrentQuantityStockAndQuantityStocksBuy = getCurrentQuantityCorporateStock() + quantityStocksBuy

        return ((multiplyCurrentStockAndWeightedAveragePrice + multiplyQuantityStockBuyAndValue) /
                sumCurrentQuantityStockAndQuantityStocksBuy).roundTo()
    }

    fun sumDamageAboutSell(value: Double, quantity: Int) =
        sumDamage(kotlin.math.abs(getCurrentWeightedAveragePrice().minus(value) * quantity))

    fun calculateProfit(stockValue: Double, quantity: Int) =
        (stockValue.minus(getCurrentWeightedAveragePrice()) * quantity).minus(getDamage()) * TAX_PERCENTAGE

    fun totalAmountLessDamage(value: Double, quantity: Int): Double {
        val totalAmount = value * quantity
        return if (totalAmount <= getDamage()) return 0.0 else kotlin.math.abs(totalAmount.minus(getDamage()))
    }
}

