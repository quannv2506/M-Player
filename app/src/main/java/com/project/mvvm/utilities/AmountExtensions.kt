package com.project.mvvm.utilities

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.pow

private const val POWER_VALUE = 10.0
private const val DEFAULT_DECIMAL = 9

private const val SCALE_VALUE_SHORT = 2
private const val SCALE_VALUE_MEDIUM = 6
private const val SCALE_VALUE_LONG = 9

private const val USD_DECIMALS = 2

private const val AMOUNT_MIN_VALUE = 0.01

fun String?.toBigDecimalOrZero(): BigDecimal {
    val removedZeros = this?.replace("(?<=\\d)\\.?0+(?![\\d\\.])", emptyString())
    return removedZeros?.toBigDecimalOrNull() ?: BigDecimal.ZERO
}

fun Int.toPowerValue(): BigDecimal =
    BigDecimal(POWER_VALUE.pow(this))

fun BigDecimal.scaleShortOrFirstNotZero(): BigDecimal {
    return if (isZero()) {
        this
    } else {
        val scale = if (scale() > SCALE_VALUE_SHORT) {
            scale() - (unscaledValue().toString().length - SCALE_VALUE_SHORT)
        } else {
            SCALE_VALUE_SHORT
        }
        setScale(scale, RoundingMode.HALF_EVEN).stripTrailingZeros() // removing zeros, case: 0.02000 -> 0.2
    }
}

fun BigDecimal.scaleShort(): BigDecimal =
    this.setScale(SCALE_VALUE_SHORT, RoundingMode.HALF_EVEN)
        .stripTrailingZeros() // removing zeros, case: 0.02000 -> 0.02

fun BigDecimal.scaleMedium(): BigDecimal =
    if (this.isZero()) this else this.setScale(SCALE_VALUE_MEDIUM, RoundingMode.HALF_EVEN)
        .stripTrailingZeros() // removing zeros, case: 0.02000 -> 0.02

fun BigDecimal.scaleLong(): BigDecimal =
    if (this.isZero()) this else this.setScale(SCALE_VALUE_LONG, RoundingMode.HALF_EVEN)
        .stripTrailingZeros() // removing zeros, case: 0.02000 -> 0.02

fun BigInteger.fromLamports(decimals: Int = DEFAULT_DECIMAL): BigDecimal =
    BigDecimal(this.toDouble() / (POWER_VALUE.pow(decimals)))
        .stripTrailingZeros() // removing zeros, case: 0.02000 -> 0.02

fun BigDecimal.toLamports(decimals: Int): BigInteger =
    this.multiply(decimals.toPowerValue()).toBigInteger()

fun BigDecimal.toUsd(usdRate: BigDecimal?): BigDecimal? =
    usdRate?.let { this.multiply(it).scaleShort() }

fun BigDecimal.formatUsd(decimals: Int = USD_DECIMALS): String = this.stripTrailingZeros().run {
    if (isZero()) this.toString() else DecimalFormatter.format(this, decimals)
} // case: 1000.023000 -> 1,000.02

fun BigDecimal.formatToken(): String = this.stripTrailingZeros().run {
    if (isZero()) this.toString() else DecimalFormatter.format(this, DEFAULT_DECIMAL)
} // case: 10000.000000007900 -> 10,000.000000008

fun BigDecimal?.isNullOrZero(): Boolean = this == null || this.compareTo(BigDecimal.ZERO) == 0
fun BigDecimal.isZero() = this.compareTo(BigDecimal.ZERO) == 0
fun BigDecimal.isNotZero() = this.compareTo(BigDecimal.ZERO) != 0
fun BigDecimal.isMoreThan(value: BigDecimal) = this.compareTo(value) == 1
fun BigDecimal.isLessThan(value: BigDecimal) = this.compareTo(value) == -1
fun BigDecimal.isZeroOrLess() = isZero() || isLessThan(BigDecimal.ZERO)

fun BigDecimal?.orZero(): BigDecimal = this ?: BigDecimal.ZERO
fun BigInteger?.orZero(): BigInteger = this ?: BigInteger.ZERO

fun BigInteger.isZero() = this.compareTo(BigInteger.ZERO) == 0
fun BigInteger.isNotZero() = this.compareTo(BigInteger.ZERO) != 0
fun BigInteger.isLessThan(value: BigInteger) = this.compareTo(value) == -1
fun BigInteger.isMoreThan(value: BigInteger) = this.compareTo(value) == 1
fun BigInteger.isZeroOrLess() = isZero() || isLessThan(BigInteger.ZERO)

fun BigDecimal.asCurrency(currency: String): String =
    if (lessThenMinValue()) "<$currency 0.01" else "$currency ${formatUsd()}"

fun BigDecimal.asUsd(): String = if (lessThenMinValue()) "<$ 0.01" else "$ ${formatUsd()}"
fun BigDecimal.asApproximateUsd(withBraces: Boolean = true): String = when {
    lessThenMinValue() -> "(<$0.01)"
    withBraces -> "~($${formatUsd()})"
    else -> "~$${formatUsd()}"
}

fun Int?.orZero(): Int = this ?: 0

// value is in (0..0.01)
private fun BigDecimal.lessThenMinValue() = !isZero() && isLessThan(AMOUNT_MIN_VALUE.toBigDecimal())
