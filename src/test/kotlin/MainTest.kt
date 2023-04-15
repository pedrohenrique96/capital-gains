import domain.Tax
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MainTest {
    @Test
    fun testarOInputDoJsonCaso1() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0))


        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 100},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50},
            {"operation":"sell", "unit-cost":15.00, "quantity": 50}]
            """.trimIndent()


        val test = taxes(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso2() {
        val expected = listOf<Tax>(Tax(0.0), Tax(10000.0), Tax(0.0))

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":5.00, "quantity": 5000}]
            """.trimIndent()

        val test = taxes(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso3() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(1000.0))

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":5.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 3000}]
            """.trimIndent()

        val test = taxes(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso4() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0))

        val json = """
            [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":25.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":15.00, "quantity": 10000}]
            """.trimIndent()

        val test = taxes(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso5() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0), Tax(10000.0))

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":25.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":15.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":25.00, "quantity": 5000}]
            """.trimIndent()

        val test = taxes(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso6() {
        val expected = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0), Tax(0.0), Tax(3000.0))

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":2.00, "quantity": 5000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
            {"operation":"sell", "unit-cost":20.00, "quantity": 2000},
            {"operation":"sell", "unit-cost":25.00, "quantity": 1000}]
            """.trimIndent()

        val test = taxes(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso7() {
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

        val test = taxes(json)

        assertEquals(expected, test)
    }

    @Test
    fun testarOInputDoJsonCaso8() {
        val expected = listOf<Tax>(Tax(0.0), Tax(80000.0), Tax(0.0), Tax(60000.0))

        val json = """
           [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":50.00, "quantity": 10000},
            {"operation":"buy", "unit-cost":20.00, "quantity": 10000},
            {"operation":"sell", "unit-cost":50.00, "quantity": 10000}]
            """.trimIndent()

        val test = taxes(json)

        assertEquals(expected, test)
    }

    @Test
    @Disabled
    fun testarOInputDoJsonCaso1Mais2() {
        val expected1 = listOf<Tax>(Tax(0.0), Tax(0.0), Tax(0.0))
        val expected2 = listOf<Tax>(Tax(0.0), Tax(10000.0), Tax(0.0))

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

        val test = taxes(json)


        assertEquals(expected1, test)
    }
}