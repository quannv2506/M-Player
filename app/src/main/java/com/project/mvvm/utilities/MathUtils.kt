package com.project.mvvm.utilities

import java.math.BigDecimal
import java.math.BigInteger

fun BigInteger.divideSafe(value: BigInteger): BigInteger =
    if (value.isZero()) BigInteger.ZERO
    else this / value

fun BigDecimal.divideSafe(value: BigDecimal): BigDecimal =
    if (value.isZero()) BigDecimal.ZERO
    else this / value
