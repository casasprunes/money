package parts.code.money

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

/**
 * An immutable representation of a money amount with a [Currency], such as `USD 8.13`.
 *
 * @property amount the amount of money
 * @property currency the currency
 * @constructor Creates an instance with a specific amount and currency
 */
data class Money(val amount: BigDecimal, val currency: Currency) {

    constructor(amount: Double, currency: Currency) : this(BigDecimal.valueOf(amount), currency)
    constructor(amount: Int, currency: Currency) : this(BigDecimal(amount), currency)
    constructor(amount: String, currency: Currency) : this(BigDecimal(amount), currency)

    companion object {
        fun zero(currency: Currency) = Money(BigDecimal.ZERO, currency)
    }

    /**
     * Returns a copy of this [Money] instance whose amount is `this.amount + money.amount`.
     *
     * @throws IllegalArgumentException if the currencies are different
     */
    operator fun plus(money: Money): Money = doIfSameCurrency(money) { copy(amount = this.amount + money.amount) }

    /**
     * Returns a copy of this [Money] instance whose amount is `this.amount - money.amount`.
     *
     * @throws IllegalArgumentException if the currencies are different
     */
    operator fun minus(money: Money): Money = doIfSameCurrency(money) { copy(amount = this.amount - money.amount) }

    /**
     * Returns a copy of this [Money] instance whose amount is `this.amount * multiplicand`.
     */
    operator fun times(multiplicand: BigDecimal): Money = copy(amount = this.amount * multiplicand)
    operator fun times(multiplicand: Double): Money = this * BigDecimal.valueOf(multiplicand)
    operator fun times(multiplicand: Int): Money = this * BigDecimal(multiplicand)

    /**
     * Returns a copy of this [Money] instance whose amount is `this.amount / divisor`.
     */
    operator fun div(divisor: BigDecimal): Money = copy(amount = this.amount.divide(divisor, MathContext.DECIMAL64))
    operator fun div(divisor: Double): Money = this / BigDecimal.valueOf(divisor)
    operator fun div(divisor: Int): Money = this / BigDecimal(divisor)

    /**
     * Compares if amounts of [Money] are >, >=, <, <=, == or !=.
     */
    operator fun compareTo(money: Money): Int = doIfSameCurrency(money) { this.amount.compareTo(money.amount) }

    /**
     * Returns a copy of this [Money] instance with the amount rounded to the given `scale` and [RoundingMode].
     */
    fun round(scale: Int, roundingMode: RoundingMode): Money = copy(amount = this.amount.setScale(scale, roundingMode))

    /**
     * Returns a copy of this [Money] instance with the amount negated.
     */
    fun negate() = copy(amount = this.amount.negate())

    private fun <T> doIfSameCurrency(money: Money, operation: (Money) -> T): T {
        return if (currency == money.currency) {
            operation(money)
        } else {
            throw IllegalArgumentException("Different currencies: $currency and ${money.currency}")
        }
    }
}
