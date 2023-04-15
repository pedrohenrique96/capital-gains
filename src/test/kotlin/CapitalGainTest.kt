import domain.Stock
import domain.Tax
import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals


class CapitalGainTest {
    @Test
    fun `Given input value then return weighted Average Price`() {
        val expected = 20.0
        val capitalGain = CapitalGain()
        val actual = capitalGain.weightedAveragePrice(5, 20.0)
        assertEquals(expected, actual)
    }

    @Test
    fun `Given input value then return weighted Average Price different`() {
        val expected = 17.0
        val capitalGain = CapitalGain()
        val actual = capitalGain.weightedAveragePrice(5, 17.0)
        assertEquals(expected, actual)
    }

    @Test
    fun `Given input value then return damage`() {
        val expected = 30.0
        val capitalGain = CapitalGain()
        capitalGain.sumDamageAboutSell(10.0, 3)
        assertEquals(expected, capitalGain.getDamage())
    }

    @Test
    fun `Given input value then return calculate profit`() {
        val expected = 18.0
        val capitalGain = CapitalGain()
        val actual = capitalGain.calculateProfit(30.0, 3)
        assertEquals(expected, actual)
    }

    @Test
    fun `Given input value shouldn't return negative number`() {
        val expected = 15.0
        val capitalGain = CapitalGain()
        capitalGain.weightedAveragePrice(5, 2.00)
        capitalGain.sumDamageAboutSell(5.0, 3)
        capitalGain.totalAmountLessDamage(10.0, 3)
        assertEquals(expected, capitalGain.getDamage())
    }

    @Test
    fun `Given input value shouldn't return negative number, validation solution`() {
        val expected = 20.0
        val capitalGain = CapitalGain()
        capitalGain.weightedAveragePrice(5, 20.0)
        capitalGain.sumDamageAboutSell(10.0, 2)
        capitalGain.totalAmountLessDamage(20.0, 3)
        assertEquals(expected, capitalGain.getDamage())
    }

    @Test
    fun `Given input value shouldn't return negative number, validation solution two`() {
        val expected = 30.0
        val capitalGain = CapitalGain()
        capitalGain.weightedAveragePrice(5, 20.0)
        capitalGain.sumDamageAboutSell(10.0, 3)
        capitalGain.totalAmountLessDamage(10.0, 3)
        assertEquals(expected, capitalGain.getDamage())
    }

    @Test
    fun `Given a stock of buy th return tax and calculate weighted average price`() {
        val capitalGain = CapitalGain()
        val actual = capitalGain.invoke(Stock("buy", 5, 20.0)).tax

        assertEquals(0.0, actual)
        assertEquals(20.0, capitalGain.getCurrentWeightedAveragePrice())
    }

    @Test
    fun `Given a stock of sell with damage th tax and register damage`() {
        val expected = 25.0
        val capitalGain = CapitalGain()
        capitalGain.invoke(Stock("buy", 5, 20.0))
        val actual = capitalGain.invoke(Stock("sell", 5, 15.0)).tax

        assertEquals(0.0, actual)
        assertEquals(capitalGain.getDamage(), expected)
    }

    @Test
    fun `Given a stock of sell without damage th return tax`() {
        val expected = 8400.0
        val capitalGain = CapitalGain()
        capitalGain.invoke(Stock("buy", 200, 20.0))
        val actual = capitalGain.invoke(Stock("sell", 150, 300.0)).tax

        assertEquals(expected, actual)
    }

    @Test
    fun `When have a register damage a next sell should paid the damage before`() {
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

        val actual = capitalGain(stock).tax

        assertEquals(expected, actual)
    }
}