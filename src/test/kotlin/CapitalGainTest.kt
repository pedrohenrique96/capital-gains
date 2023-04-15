import domain.Stock
import domain.Tax
import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals


class CapitalGainTest {
    @Test
    fun weightedAveragePrice() {
        val expected = 20.0
        val capitalGain = CapitalGain()
        val weightedAveragePrice = capitalGain.weightedAveragePrice(5, 20.0)
        assertEquals(expected, weightedAveragePrice)
    }

    @Test
    fun weightedAveragePrice2() {
        val expected = 17.0
        val capitalGain = CapitalGain()
        val weightedAveragePrice2 = capitalGain.weightedAveragePrice(5, 17.0)
        assertEquals(expected, weightedAveragePrice2)
    }

    @Test
    fun valorDoPrejuizoDeVenda() {
        val expected = 30.0
        val capitalGain = CapitalGain()
        capitalGain.sumDamageAboutSell(10.0, 3)
        assertEquals(expected, capitalGain.getDamage())
    }

    @Test
    fun calcularLucro() {
        val expected = 18.0
        val capitalGain = CapitalGain()
        val calcularLucro = capitalGain.calculateProfit(30.0, 3)
        assertEquals(expected, calcularLucro)
    }

    @Test
    fun valorTotalDaOperacaoDeduzidoOPrejuMasDeveSerZeroPorqueTemMuitoPrejuizoParaPouco() {
        val expected = 15.0
        val capitalGain = CapitalGain()
        capitalGain.weightedAveragePrice(5, 2.00)
        capitalGain.sumDamageAboutSell(5.0, 3)
        capitalGain.totalAmountLessDamage(10.0, 3)
        assertEquals(expected, capitalGain.getDamage())
    }

    @Test
    fun valorTotalDaOperacaoDeduzidoOPrejut() {
        val expected = 20.0
        val capitalGain = CapitalGain()
        capitalGain.weightedAveragePrice(5, 20.0)
        capitalGain.sumDamageAboutSell(10.0, 2)
        capitalGain.totalAmountLessDamage(20.0, 3)
        assertEquals(expected, capitalGain.getDamage())
    }

    @Test
    fun valorTotalDaOperacaoDeduzidoOPreju2() {
        val expected = 30.0
        val capitalGain = CapitalGain()
        capitalGain.weightedAveragePrice(5, 20.0)
        capitalGain.sumDamageAboutSell(10.0, 3)
        capitalGain.totalAmountLessDamage(10.0, 3)
        assertEquals(expected, capitalGain.getDamage())
    }

    @Test
    fun testJuntarTudoQuandoCompra() {
        val capitalGain = CapitalGain()
        val juntar = capitalGain.invoke(Stock("buy", 5, 20.0))

        assertEquals(0.0, juntar.tax)
        assertEquals(20.0, capitalGain.getCurrentWeightedAveragePrice())
    }

    @Test
    fun testJuntarTudoQuandoVenda() {
        val expectedPrejuizo = 25.0
        val capitalGain = CapitalGain()
        capitalGain.invoke(Stock("buy", 5, 20.0))
        val juntar = capitalGain.invoke(Stock("sell", 5, 15.0))

        assertEquals(0.0, juntar.tax)
        assertEquals(capitalGain.getDamage(), expectedPrejuizo)
    }

    @Test
    fun testJuntarTudoQuandoVenda2() {
        val expected = 8400.0
        val capitalGain = CapitalGain()
        capitalGain.invoke(Stock("buy", 200, 20.0))
        val juntar = capitalGain.invoke(Stock("sell", 150, 300.0))

        assertEquals(expected, juntar.tax)
    }

    @Test
    fun testQuandoDaErroNoTipo6() {
        val capitalGain = CapitalGain()

        capitalGain.invoke(Stock("buy", 10000, 10.0))
        capitalGain.invoke(Stock("sell", 5000, 2.0))
        assertEquals(40000.0, capitalGain.getDamage())

        capitalGain.invoke(Stock("sell", 2000, 20.0))
        assertEquals(20000.0, capitalGain.getDamage())
    }

    @Test
    fun `When operation is invalid`() {
        val expected = -1.0
        val capitalGain = CapitalGain()
        val stock = Stock("invalid", 10000, 10.0)

        val result = capitalGain(stock)

        assertEquals(expected, result.tax)
    }
}