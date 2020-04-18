package parts.code.money

data class Currency(val currencyCode: CurrencyCode) {

    constructor(currencyCode: String) : this(CurrencyCode(currencyCode))
}
