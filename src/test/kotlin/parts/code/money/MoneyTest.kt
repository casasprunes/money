package parts.code.money

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import parts.code.money.Currency.EUR
import parts.code.money.Currency.USD
import java.math.BigDecimal
import java.math.RoundingMode

class MoneyTest {

    @Test
    fun `should create money instances by different types`() {
        assertEquals(BigDecimal.valueOf(0.11), Money(BigDecimal.valueOf(0.11), USD).amount)
        assertEquals(BigDecimal.valueOf(0.11), Money(0.11, USD).amount)
        assertEquals(BigDecimal(0), Money(0, USD).amount)
        assertEquals(BigDecimal("0.11"), Money("0.11", USD).amount)
        assertEquals(Money(BigDecimal.ZERO, USD), Money.zero(USD))
    }

    @Test
    fun `should not sum amounts of money with different currencies`() {
        assertThrows<IllegalArgumentException> { Money(0.11, USD) + Money(2.35, EUR) }
    }

    @ParameterizedTest
    @CsvSource(
        "0.11 , 0.0     , 0.11",
        "0.11 , 2.35    , 2.46",
        "0.11 , -2.35   , -2.24",
        "0.11 , 2.35813 , 2.46813"
    )
    fun `should sum amounts of money`(amount1: Double, amount2: Double, result: Double) {
        assertEquals(Money(result, USD), Money(amount1, USD) + Money(amount2, USD))
    }

    @Test
    fun `should not subtract amounts of money with different currencies`() {
        assertThrows<IllegalArgumentException> { Money(0.11, USD) - Money(2.35, EUR) }
    }

    @ParameterizedTest
    @CsvSource(
        "0.11 , 0.0     , 0.11",
        "0.11 , 2.35    , -2.24",
        "0.11 , -2.35   , 2.46",
        "0.11 , 2.35813 , -2.24813"
    )
    fun `should subtract amounts of money`(amount1: Double, amount2: Double, result: Double) {
        assertEquals(Money(result, USD), Money(amount1, USD) - Money(amount2, USD))
    }

    @Test
    fun `should multiply amounts of money`() {
        assertEquals(Money(6, USD), Money(2, USD) * 3)
    }

    @ParameterizedTest
    @CsvSource(
        "0.11 , 0.0     , 0.000",
        "0.11 , 2.35    , 0.2585",
        "0.11 , -2.35   , -0.2585",
        "0.11 , 2.35813 , 0.2593943"
    )
    fun `should multiply amounts of money by decimal numbers`(
        amount: Double,
        multiplicand: Double,
        result: BigDecimal
    ) {
        assertEquals(Money(result, USD), Money(amount, USD) * multiplicand)
        assertEquals(Money(result, USD), Money(amount, USD) * BigDecimal.valueOf(multiplicand))
    }

    @Test
    fun `should divide amounts of money`() {
        assertEquals(Money(0.6666666666666667, USD), Money(2, USD) / 3)
    }

    @ParameterizedTest
    @CsvSource(
        "0.11 , 2.35    , 0.04680851063829787",
        "0.11 , -2.35   , -0.04680851063829787",
        "0.11 , 2.35813 , 0.04664713141345049"
    )
    fun `should divide amounts of money by decimal numbers`(amount: Double, divisor: Double, result: BigDecimal) {
        assertEquals(Money(result, USD), Money(amount, USD) / divisor)
        assertEquals(Money(result, USD), Money(amount, USD) / BigDecimal.valueOf(divisor))
    }

    @Test
    fun `should compare amounts of money`() {
        assertTrue((Money(2.35, USD) > Money(0.11, USD)))
        assertTrue((Money(0.11, USD) >= Money(0.11, USD)))
        assertTrue((Money(2.35, USD) >= Money(0.11, USD)))
        assertTrue((Money(0.11, USD) < Money(2.35, USD)))
        assertTrue((Money(0.11, USD) <= Money(0.11, USD)))
        assertTrue((Money(0.11, USD) <= Money(2.35, USD)))
        assertTrue((Money(0.11, USD) == Money(0.11, USD)))
        assertTrue((Money(0.11, USD) != Money(2.35, USD)))
    }

    @Test
    fun `should round amounts of money`() {
        assertEquals(Money(0.1, USD), Money(0.112358, USD).round(1, RoundingMode.HALF_EVEN))
        assertEquals(Money(0.11, USD), Money(0.112358, USD).round(2, RoundingMode.HALF_EVEN))
        assertEquals(Money(0.112, USD), Money(0.112358, USD).round(3, RoundingMode.HALF_EVEN))
        assertEquals(Money(0.1124, USD), Money(0.112358, USD).round(4, RoundingMode.HALF_EVEN))
        assertEquals(Money(0.11236, USD), Money(0.112358, USD).round(5, RoundingMode.HALF_EVEN))
    }
}
