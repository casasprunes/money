![Build](https://github.com/casasprunes/money/workflows/Build/badge.svg)
[![codecov](https://codecov.io/gh/casasprunes/money/branch/master/graph/badge.svg)](https://codecov.io/gh/casasprunes/money)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

# Money

Simple money and currency library written in Kotlin

## Examples

```kotlin
import parts.code.money.Currency.EUR
import parts.code.money.Currency.USD

// create money instances by different types 
val money = Money(1123, USD)
val money = Money(0.11, USD)
val money = Money("0.11", USD)
val money = Money(BigDecimal.valueOf(0.11), USD)
val money = Money.zero(USD)

// sum and subtract amounts of money
val money = Money(0.11, USD) + Money(2.35, USD)
val money = Money(0.11, USD) - Money(2.35, USD)

// multiply and divide amounts of money
val money = Money(0.11, USD) * 3
val money = Money(0.11, USD) / 3

// compare amounts of money
Money(2.35, USD) > Money(0.11, USD)
Money(0.11, USD) == Money(0.11, USD)
Money(0.11, USD) != Money(2.35, USD)

// round amounts of money
Money(0.112, USD) == Money(0.112358, USD).round(3, RoundingMode.HALF_EVEN)

// negate amounts of money
Money(-0.1, USD) == Money(0.1, USD).negate()

// throw IllegalArgumentException when operating with different currencies
Money(0.11, USD) + Money(2.35, EUR)
```

## Getting Started

Add `jcenter()` in your root `build.gradle`

```gradle
allprojects {
    repositories {
        jcenter()
    }
}
```

Add the dependency

```gradle
dependencies {
    compile 'parts.code:money:1.0.0'
}
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
