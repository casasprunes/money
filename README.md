![Build](https://github.com/casasprunes/money/workflows/Build/badge.svg)
[![codecov](https://codecov.io/gh/casasprunes/money/branch/master/graph/badge.svg)](https://codecov.io/gh/casasprunes/money)
[![Download](https://api.bintray.com/packages/casasprunes/money/parts.code.money/images/download.svg?version=1.0.0)](https://bintray.com/casasprunes/money/parts.code.money/1.0.0/link)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## :star: Overview

The goals of this library are: 
* To put *amount* and *currency* together into a data class
* To be able to perform operations with Money instances using symbolic operators
* To have the safety of not performing operations between different currencies
* To have an enum with the active alphabetic codes of official ISO 4217 currency names

It's a simple library that tries to do one thing and do it well, instead of trying to solve many problems. 

## :inbox_tray: Installation

### Maven

```xml
<repository>
    <id>jcenter</id>
    <name>jcenter</name>
    <url>https://jcenter.bintray.com</url>
</repository>

<dependency>
    <groupId>parts.code</groupId>
    <artifactId>money</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

```gradle
repositories {
    jcenter()
}

dependencies {
    compile 'parts.code:money:1.0.0'
}
```

## :rocket: Usage

### Money class

The Money class is an immutable representation of a money amount with currency, such as *USD 8.13*. We can instantiate it with amounts in different types *Int*, *Double*, *BigDecimal* and *String*.

```kotlin
val moneyFromInt = Money(11, USD)
val moneyFromDouble = Money(0.11, USD)
val moneyFromBigDecimal = Money(BigDecimal.valueOf(0.11), USD)
val moneyFromString = Money("0.11", USD)
```

We can also instantiate it by using the *zero* method:

```kotlin
val money = Money.zero(USD)
```

### Arithmetic Operations

The Money class uses operator overloading to provide implementations for the arithmetic operators sum, subtract, multiply and divide, allowing us to perform operations in a simple way, without having to call any methods:

```kotlin
val moneySum = Money(0.11, USD) + Money(2.35, USD)
val moneySubtract = Money(0.11, USD) - Money(2.35, USD)
val moneyMultiply = Money(0.11, USD) * 3
val moneyDivide = Money(0.11, USD) / 3
```

Having the safety of not performing operations between different currencies:

```kotlin
assertThrows<IllegalArgumentException> {
    val moneyException = Money(0.11, USD) + Money(2.35, EUR)
}
```

### Comparison

We can compare amounts of money by using the comparison operators:

```kotlin
val isGreater = Money(2.35, USD) > Money(0.11, USD)
val isGreaterOrEquals = Money(0.11, USD) >= Money(0.11, USD)
val isEquals = Money(0.11, USD) == Money(0.11, USD)
val isNotEquals = Money(0.11, USD) != Money(2.35, USD)
val isLower = Money(0.11, USD) < Money(2.35, USD)
val isLowerOrEquals = Money(0.11, USD) <= Money(0.11, USD)
```

### Rounding

We can use the *round* method to round an amount of money by a scale and rounding mode:

```kotlin
val moneyRound = Money(0.112358, USD).round(3, RoundingMode.HALF_EVEN)
```

## ⚖️ License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
