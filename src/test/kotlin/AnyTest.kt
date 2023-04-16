import utils.extension.roundTo
import org.junit.jupiter.api.Test
import utils.extension.fromValueToString
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
}