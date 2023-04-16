import domain.Tax
import org.junit.jupiter.api.Test
import utils.extension.fromValueToString
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class MainTest {

    @Test
    fun `Case one should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0)).fromValueToString()

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 100},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50}]
            """.trimIndent()

        executeWriteInStdin(json)

        val output = executeApplication()

        val actual = processOutput(output)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case two should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(10000.0), Tax(0.0)).fromValueToString()

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":5.00, "quantity": 5000}]
            """.trimIndent()

        executeWriteInStdin(json)

        val output = executeApplication()

        val actual = processOutput(output)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case tree should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(1000.0)).fromValueToString()

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":5.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 3000}]
            """.trimIndent()

        executeWriteInStdin(json)

        val output = executeApplication()

        val actual = processOutput(output)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case four should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0)).fromValueToString()

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":25.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":15.00, "quantity": 10000}]
            """.trimIndent()

        executeWriteInStdin(json)

        val output = executeApplication()

        val actual = processOutput(output)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case five should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0),
            Tax(0.0), Tax(10000.0)).fromValueToString()

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":25.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":15.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":25.00, "quantity": 5000}]
            """.trimIndent()

        executeWriteInStdin(json)

        val output = executeApplication()

        val actual = processOutput(output)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case six should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0),
            Tax(0.0), Tax(0.0), Tax(3000.0)).fromValueToString()

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":2.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
            {"operation":"sell", "unit-cost":25.00, "quantity": 1000}]
            """.trimIndent()

        executeWriteInStdin(json)

        val output = executeApplication()

        val actual = processOutput(output)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case seven should return taxes`() {
        val expected = listOf<Tax>(
            Tax(0.0), Tax(0.0), Tax(0.0),
            Tax(0.0), Tax(3000.0), Tax(0.0),
            Tax(0.0), Tax(3700.0), Tax(0.0)
        ).fromValueToString()

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

        executeWriteInStdin(json)

        val output = executeApplication()

        val actual = processOutput(output)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case eight should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(80000.0), Tax(0.0), Tax(60000.0)).fromValueToString()

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":50.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":20.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":50.00, "quantity": 10000}]
            """.trimIndent()

        executeWriteInStdin(json)

        val output = executeApplication()

        val actual = processOutput(output)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case two plus tree return two taxes`() {
        val firstExpected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0)).fromValueToString()
        val secondExpected = listOf<Tax>(Tax(0.0), Tax(10000.0), Tax(0.0)).fromValueToString()

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 100},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50}]
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":5.00, "quantity": 5000}]""".trimIndent()

        executeWriteInStdin(json)

        val output = executeApplication()

        val firstActual = processOutput(output)
        assertEquals(firstExpected, firstActual)

        val secondActual = processSecondOutput(output)
        assertEquals(secondExpected, secondActual)
    }

    private fun executeApplication(): String {
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))

        main()

        return outContent.toString().trim()
    }

    private fun executeWriteInStdin(input: String) {
        System.setIn(ByteArrayInputStream(input.toByteArray()))
        System.setIn(System.`in`)
    }

    private fun processOutput(output: String): String {
        return output.lineSequence().drop(1).first()
    }

    private fun processSecondOutput(output: String): String {
        return output.lineSequence().drop(2).first()
    }
}