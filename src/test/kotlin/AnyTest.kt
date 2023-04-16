import domain.Stock
import utils.extension.roundTo
import org.junit.jupiter.api.Test
import utils.extension.fromValueToString
import utils.extension.transformStringInArray
import kotlin.test.assertEquals

class AnyTest {
    @Test
    fun `Given a value when roundTo then return value rounded to two decimal places`(){
        val expected = 16.67
        val value = 16.6666667
        val actual = value.roundTo()
        assertEquals(expected, actual)
    }

    @Test
    fun `fromValueToString with valid value returns a String instance`() {
        val expected = "1234"
        val value = 1234
        val actual = value.fromValueToString()

        assertEquals(expected, actual)
    }

    @Test
    fun `transformStringInArray with valid value returns a list of lists of stock`() {
        val expected = listOf<List<Stock>>(listOf(Stock("buy", 100, 10.00)),
            listOf(Stock("buy", 100, 10.00))).fromValueToString()
        val value = """
            [{"operation":"buy", "quantity": 100, "unit-cost":10.0 }]
            [{"operation":"buy", "quantity": 100, "unit-cost":10.00}]
            """.trimIndent()

        val actual = value.transformStringInArray().fromValueToString()

        assertEquals(expected, actual)
    }
}