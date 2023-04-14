import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*

//
//var precoMedioPonderado = 0
//var quantidadesDeacoesAtual = 0
//var prejuizoMaluco = 0
//
//if operation == "buy":
//precoMedioPonderado = calcularOPrecoMedioPonderado(5, 20)
//return {tax: 0}
//else if operation == sell:
//if do preju (precoMedioPonderado >= valorDaAcaoDeVenda):
//prejuizoMaluco = valorDoPrejuizoNessaVenda(valorDaAcaoDeVenda, quantidade)
//return 0
//
//else if sem preju (precoMedioPonderado < valorDaAcaoDeVenda)
//valorTotal = valorTotalDaOperacaoDeduzidoOPreju();
//if valorTotal <= 20000
//return {tax: 0}
//else:
//return {tax: (calcularLucro(valorTotal) * 0.20)}
//
//funcao calcularOPrecoMedioPonderado(quantidadeAcoesComprada, valorDaCompra) {
//

//}
//
//
//// Essa funcao deve abater o prejuizo até que ele fica zerado
//funcao valorDoPrejuizoNessaVenda(valorDaAcaoDeVenda, quantidade) {
//    quantidadesDeacoesAtual = quantidadesDeacoesAtual -= quantidade
//    return (valorDaAcaoDeVenda - precoMedioPonderado) * quantidade
//}
//
//// verificar se combrara imposto e deduz prejuizo para novo valor
//funcao valorTotalDaOperacaoDeduzidoOPreju(custoUnitárioDaAção, quantidade) {
//    quantidadesDeacoesAtual = quantidadesDeacoesAtual -= quantidade
//    var calculoTotal = custoUnitárioDaAção x quantidade
//    calcularDiferencaEAtualizarPrejuizo ()
//    return calculoTotal - prejuizoMaluco // Nunca negativo e pegar valor negativo e adicionar como prejuizo novo dado o exemploe retorna 0
//
//    ex: 100 - 150 = -50
//    // Novo prejuizo 50
//}
//
//funcao calcularDiferencaEAtualizarPrejuizo() {
//    prejuizoMaluco = calculoTotal - prejuizoMaluco // Nunca Negativo pls
//}
//
////difenca entre
//calcularLucro(valorDaAcao, quantidade) {
//    var valor = (valorDaAcao - precoMedioPonderado) // pegar a diferenca
//
//    var lucro = valor * quantidade
//
//    return lucro
//}



fun main(args: Array<String>) {
    println("Hello World!")

    val main = Main()


}

fun testao(json: String): List<Taxa> {

    val array = Json.decodeFromString<Array<Acao>>(json)
    val main = Main()

    return array.map { acao ->
        main.juntarTodo(acao)
    }
}

@Serializable
data class Acao(val operation: String,
                val quantity: Int,
                @JsonNames("unit-cost")
                val unitCost: Double)

data class Taxa(val valor: Double)

class Main {
    private var precoMedioPonderado = 0.0
    private var prejuizoMaluco = 0.0
    private var quantidadesDeacoesAtual = 0

    private var taxa = mutableListOf<Taxa>()
    fun juntarTodo(acao: Acao): Taxa {
        if (acao.operation == "buy") {
            precoMedioPonderado = weightedAveragePrice(acao.quantity, acao.unitCost)
            quantidadesDeacoesAtual += acao.quantity
            taxa.add(Taxa(0.0))
            return Taxa(0.0)
        } else if (acao.operation == "sell") {
            return if (precoMedioPonderado >= acao.unitCost) {
                valorDoPrejuizoDeVenda(acao.unitCost, acao.quantity)
                taxa.add(Taxa(0.0))
                Taxa(0.0)
            } else {
                val valorTotal = valorTotalDaOperacaoDeduzidoOPreju(acao.unitCost, acao.quantity)
                if (valorTotal <= 20000) {
                    taxa.add(Taxa(0.0))
                    Taxa(0.0)
                } else {
                    val calcular = calcularLucro(acao.unitCost, acao.quantity) * 0.20
                    taxa.add(Taxa(calcular))
                    Taxa(calcular)
                }
            }
        }

        taxa.add(Taxa(0.0))
        return Taxa(0.0)
    }

    fun getPrecoMedioPonderado(): Double {
        return precoMedioPonderado
    }

    fun getPrejuizoMaluco(): Double {
        return prejuizoMaluco
    }

    fun getQuantidadesDeacoesAtual(): Int {
        return quantidadesDeacoesAtual
    }
    fun weightedAveragePrice(quantidadeAcoesComprada: Int, valorDaCompra: Double): Double {
        return ((getQuantidadesDeacoesAtual() * getPrecoMedioPonderado()) + (quantidadeAcoesComprada * valorDaCompra)) /
                (getQuantidadesDeacoesAtual() + quantidadeAcoesComprada)
    }

    fun valorDoPrejuizoDeVenda(valorDaAcaoDeVenda: Double, quantidade: Int): Double {
        return sumPrejuizo(kotlin.math.abs(getPrecoMedioPonderado().minus(valorDaAcaoDeVenda) * quantidade))
    }

    fun calcularDiferencaPrejuizo(valorTotal: Double): Double {
        return kotlin.math.abs(valorTotal.minus(getPrejuizoMaluco()))
    }

    private fun sumPrejuizo(valor: Double): Double {
        prejuizoMaluco += valor
        return prejuizoMaluco
    }

    private fun subPrejuizo(valor: Double) {
        prejuizoMaluco = kotlin.math.abs(getPrejuizoMaluco().minus(valor))
    }

    fun calcularLucro(valorDaAcao: Double, quantidade: Int): Double {
        val lucro =  valorDaAcao.minus(getPrecoMedioPonderado()) * quantidade
        return lucro.minus(getPrejuizoMaluco())

    }

    fun valorTotalDaOperacaoDeduzidoOPreju(custoUnitárioDaAção: Double, quantidade: Int): Double {
        val valorTotal = custoUnitárioDaAção * quantidade

        if (valorTotal <= getPrejuizoMaluco()) {
            subPrejuizo(valorTotal)
            return 0.0
        } else if (getPrejuizoMaluco() > 0) {
            subPrejuizo(getPrejuizoMaluco())
            return calcularDiferencaPrejuizo(valorTotal)
        }

        return calcularDiferencaPrejuizo(valorTotal)
    }
}

