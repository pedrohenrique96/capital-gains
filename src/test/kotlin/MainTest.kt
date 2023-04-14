import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals


class MainTest {
    @Test
    fun weightedAveragePrice() {
        val expected = 20.0
        val main = Main()
        val weightedAveragePrice = main.weightedAveragePrice(5, 20.0)
        assertEquals(expected, weightedAveragePrice)
    }

    @Test
    fun weightedAveragePrice2() {
        val expected = 17.0
        val main = Main()
        val weightedAveragePrice2 = main.weightedAveragePrice(5, 17.0)
        assertEquals(expected, weightedAveragePrice2)
    }

    @Test
    fun valorDoPrejuizoDeVenda() {
        val expected = 30.0
        val main = Main()
        val valorDoPrejuizoDeVenda = main.valorDoPrejuizoDeVenda(10.0, 3)
        assertEquals(expected, valorDoPrejuizoDeVenda)
    }

    @Test
    fun calcularDiferencaEAtualizarPrejuizoQuandoésuficiente() {
        val expected = 20.0
        val main = Main()
        val calcularDiferencaEAtualizarPrejuizo = main.calcularDiferencaPrejuizo(20.0)
        assertEquals(expected, calcularDiferencaEAtualizarPrejuizo)
    }

    @Test
    fun calcularLucro() {
        val expected = 90.0
        val main = Main()
        val calcularLucro = main.calcularLucro(30.0, 3)
        assertEquals(expected, calcularLucro)
    }

    @Test
    fun valorTotalDaOperacaoDeduzidoOPrejuMasDeveSerZeroPorqueTemMuitoPrejuizoParaPouco() {
        val expected = 15.0
        val main = Main()
        main.weightedAveragePrice(5, 2.00)
        main.valorDoPrejuizoDeVenda(5.0, 3)
        main.valorTotalDaOperacaoDeduzidoOPreju(10.0, 3)
        assertEquals(expected, main.getPrejuizoMaluco())
    }

    @Test
    fun valorTotalDaOperacaoDeduzidoOPrejut() {
        val expected = 20.0
        val main = Main()
        main.weightedAveragePrice(5, 20.0)
        main.valorDoPrejuizoDeVenda(10.0, 2)
        main.valorTotalDaOperacaoDeduzidoOPreju(20.0, 3)
        assertEquals(expected, main.getPrejuizoMaluco())
    }

    @Test
    fun valorTotalDaOperacaoDeduzidoOPreju2() {
        val expected = 30.0
        val main = Main()
        main.weightedAveragePrice(5, 20.0)
        main.valorDoPrejuizoDeVenda(10.0, 3)
        main.valorTotalDaOperacaoDeduzidoOPreju(10.0, 3)
        assertEquals(expected, main.getPrejuizoMaluco())
    }

    @Test
    fun testJuntarTudoQuandoCompra() {
        val main = Main()
        val juntar = main.juntarTodo(Acao("buy", 5, 20.0))

        assertEquals(0.0, juntar.valor)
        assertEquals(20.0, main.getPrecoMedioPonderado())
    }

    @Test
    fun testJuntarTudoQuandoVenda() {
        val expectedPrejuizo = 25.0
        val main = Main()
        main.juntarTodo(Acao("buy", 5, 20.0))
        val juntar = main.juntarTodo(Acao("sell", 5, 15.0))

        assertEquals(0.0, juntar.valor)
        assertEquals(main.getPrejuizoMaluco(), expectedPrejuizo)
    }

    @Test
    fun testJuntarTudoQuandoVenda2() {
        val expected = 8400.0
        val main = Main()
        main.juntarTodo(Acao("buy", 200, 20.0))
        val juntar = main.juntarTodo(Acao("sell", 150, 300.0))

        assertEquals(expected, juntar.valor)
    }

    @Test
    fun testQuandoDaErroNoTipo6() {
        val main = Main()

        main.juntarTodo(Acao("buy", 10000, 10.0))
        main.juntarTodo(Acao("sell", 5000, 2.0))
        assertEquals(40000.0, main.getPrejuizoMaluco())

        main.juntarTodo(Acao("sell", 2000, 20.0))
        assertEquals(20000.0, main.getPrejuizoMaluco())
    }


    @Test
    fun testarOInputDoJsonCaso1() {
        val expected = listOf<Taxa>(Taxa(0.0), Taxa(0.0), Taxa(0.0))


        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 100},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50}]
            """.trimIndent()


        val test = testao(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso2() {
        val expected = listOf<Taxa>(Taxa(0.0), Taxa(10000.0), Taxa(0.0))

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":5.00, "quantity": 5000}]
            """.trimIndent()

        val test = testao(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso3() {
        val expected = listOf<Taxa>(Taxa(0.0), Taxa(0.0), Taxa(1000.0))

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":5.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 3000}]
            """.trimIndent()

        val test = testao(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso4() {
        val expected = listOf<Taxa>(Taxa(0.0), Taxa(0.0), Taxa(0.0))

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":25.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":15.00, "quantity": 10000}]
            """.trimIndent()

        val test = testao(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso5() {
        val expected = listOf<Taxa>(Taxa(0.0), Taxa(0.0), Taxa(0.0), Taxa(10000.0))

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":25.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":15.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":25.00, "quantity": 5000}]
            """.trimIndent()

        val test = testao(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso6() {
        val expected = listOf<Taxa>(Taxa(0.0), Taxa(0.0), Taxa(0.0), Taxa(0.0), Taxa(3000.0))

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":2.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
            {"operation":"sell", "unit-cost":25.00, "quantity": 1000}]
            """.trimIndent()

        val test = testao(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso7() {
        val expected = listOf<Taxa>(Taxa(0.0), Taxa(0.0), Taxa(0.0),
                Taxa(0.0), Taxa(3000.0), Taxa(0.0), Taxa(0.0), Taxa(3700.0), Taxa(0.0))

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":2.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
            {"operation":"sell", "unit-cost":25.00, "quantity": 1000},
            {"operation":"buy", "unit-cost":20.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":15.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":30.00, "quantity": 4350},
            {"operation":"sell", "unit-cost":30.00, "quantity": 650}]
            """.trimIndent()

        val test = testao(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso8() {
        val expected = listOf<Taxa>(Taxa(0.0), Taxa(80000.0), Taxa(0.0), Taxa(60000.0))

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":50.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":20.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":50.00, "quantity": 10000}]
            """.trimIndent()

        val test = testao(json)

        assertEquals(expected, test)
    }

    @Test
    @Disabled
    fun testarOInputDoJsonCaso1Mais2() {
        val expected1 = listOf<Taxa>(Taxa(0.0), Taxa(0.0), Taxa(0.0))
        val expected2 = listOf<Taxa>(Taxa(0.0), Taxa(10000.0), Taxa(0.0))

        val json = """
            [
                {"operation":"buy", "unit-cost":10.00, "quantity": 100},
                {"operation":"sell", "unit-cost":15.00, "quantity": 50},
                {"operation":"sell", "unit-cost":15.00, "quantity": 50}
            ],
            [
                {"operation":"buy", "unit-cost":10.00, "quantity": 10000},
                {"operation":"sell", "unit-cost":20.00, "quantity": 5000},
                {"operation":"sell", "unit-cost":5.00, "quantity": 5000}
            ]""".trimIndent()

        val test = testao(json)


        assertEquals(expected1, test)
    }

}