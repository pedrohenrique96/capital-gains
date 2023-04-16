import domain.Tax
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MainTest {
    @Test
    fun `Case one should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0))

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 100},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50}]
            """.trimIndent()

        val actual = taxes(json)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case two should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(10000.0), Tax(0.0))

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":5.00, "quantity": 5000}]
            """.trimIndent()

        val actual = taxes(json)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case tree should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(1000.0))

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":5.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 3000}]
            """.trimIndent()

        val actual = taxes(json)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case four should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0))

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":25.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":15.00, "quantity": 10000}]
            """.trimIndent()

        val actual = taxes(json)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case five should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0), Tax(10000.0))

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":25.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":15.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":25.00, "quantity": 5000}]
            """.trimIndent()

        val actual = taxes(json)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case six should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0), Tax(0.0), Tax(3000.0))

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":2.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
            {"operation":"sell", "unit-cost":25.00, "quantity": 1000}]
            """.trimIndent()

        val actual = taxes(json)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case seven should return taxes`() {
        val expected = listOf<Tax>(
            Tax(0.0), Tax(0.0), Tax(0.0),
            Tax(0.0), Tax(3000.0), Tax(0.0), Tax(0.0), Tax(3700.0), Tax(0.0)
        )

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

        val actual = taxes(json)

        assertEquals(expected, actual)
    }

    @Test
    fun `Case eight should return taxes`() {
        val expected = listOf<Tax>(Tax(0.0), Tax(80000.0), Tax(0.0), Tax(60000.0))

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":50.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":20.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":50.00, "quantity": 10000}]
            """.trimIndent()

        val actual = taxes(json)

        assertEquals(expected, actual)
    }

    @Test
    @Disabled
    fun testarOInputDoJsonCaso1Mais2() {
        val expected1 = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0))
        val expected2 = listOf<Tax>(Tax(0.0), Tax(10000.0), Tax(0.0))

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 100},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50}]
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":5.00, "quantity": 5000}]""".trimIndent()

        val actual = taxes(json)

        assertEquals(expected1, actual)
    }
}