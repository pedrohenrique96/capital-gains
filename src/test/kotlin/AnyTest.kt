import utils.extension.roundTo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class AnyTest {
    @Test
    fun `Given a value when roundTo then return value rounded to two decimal places`(){
        val expected = 16.67
        val value = 16.6666667
        val actual = value.roundTo()
        assertEquals(expected, actual)
    }
}