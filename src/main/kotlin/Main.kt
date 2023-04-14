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
    private var damage = 0.0
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
                subQuantidadeDeAcoes(corporateStock.quantity)
                return if (currentWeightedAveragePrice >= corporateStock.unitCost) {
                    valorDoPrejuizoDeVenda(corporateStock.unitCost, corporateStock.quantity)
                    tax.add(Tax(TAX_ZERO))
                    Tax(TAX_ZERO)
                } else {
                    val valorTotal = valorTotalDaOperacaoDeduzidoOPreju(corporateStock.unitCost, corporateStock.quantity)
                    if (valorTotal <= MINIMUM_AMOUNT_TO_CHARGE_TAX) {
                        val deduzir = kotlin.math.abs((this.getCurrentWeightedAveragePrice().minus(corporateStock.unitCost) * corporateStock.quantity))
                        subPrejuizo(deduzir)
                        tax.add(Tax(TAX_ZERO))
                        Tax(TAX_ZERO)
                    } else {
                        val calcular = calcularLucro(corporateStock.unitCost, corporateStock.quantity) * TAX_PERCENTAGE
                        tax.add(Tax(calcular))
                        Tax(calcular)
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

    fun getPrejuizoMaluco(): Double {
        return damage
    }

    fun getCurrentQuantityCorporateStock(): Int {
        return currentQuantityCorporateStock
    }

    fun subQuantidadeDeAcoes(quantidade: Int) {
        currentQuantityCorporateStock -= quantidade
    }
    fun weightedAveragePrice(quantidadeAcoesComprada: Int, valorDaCompra: Double): Double {
        return ((this.getCurrentQuantityCorporateStock() * this.getCurrentWeightedAveragePrice()) + (quantidadeAcoesComprada * valorDaCompra)) /
                (this.getCurrentQuantityCorporateStock() + quantidadeAcoesComprada)
    }

    fun valorDoPrejuizoDeVenda(valorDaAcaoDeVenda: Double, quantidade: Int): Double {
        return sumPrejuizo(kotlin.math.abs(getCurrentWeightedAveragePrice().minus(valorDaAcaoDeVenda) * quantidade))
    }

    fun calcularDiferencaPrejuizo(valorTotal: Double): Double {
        return kotlin.math.abs(valorTotal.minus(getPrejuizoMaluco()))
    }

    private fun sumPrejuizo(valor: Double): Double {
        damage += valor
        return damage
    }

    private fun subPrejuizo(valor: Double) {
        damage = kotlin.math.abs(damage.minus(valor))
    }

    fun calcularLucro(valorDaAcao: Double, quantidade: Int): Double {
        val lucro =  valorDaAcao.minus(this.getCurrentWeightedAveragePrice()) * quantidade
        return lucro.minus(getPrejuizoMaluco())

    }

    fun valorTotalDaOperacaoDeduzidoOPreju(custoUnitárioDaAção: Double, quantidade: Int): Double {
        val valorTotal = custoUnitárioDaAção * quantidade

        if (valorTotal <= getPrejuizoMaluco()) {
            return 0.0
        }

        return calcularDiferencaPrejuizo(valorTotal)
    }
}

