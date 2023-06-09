package com.project.mvvm.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.mvvm.utilities.toBigDecimalOrZero
import com.project.mvvm.utilities.toBigIntegerOrDefaultZero
import java.math.BigDecimal
import java.math.BigInteger

class DataTypeConverter {
    @TypeConverter
    fun stringToDecimal(data: String?): BigDecimal {
        return data.toBigDecimalOrZero()
    }

    @TypeConverter
    fun bigDecimalToString(decimal: BigDecimal): String {
        return decimal.toPlainString()
    }
}

class BigIntegerDataTypeConverter {
    @TypeConverter
    fun stringToBigInteger(data: String?): BigInteger {
        return data.toBigIntegerOrDefaultZero()
    }

    @TypeConverter
    fun bigIntegerToString(bigInteger: BigInteger): String {
        return bigInteger.toString()
    }
}

/*
class TransactionTypeConverter {
    @TypeConverter
    fun transactionTypeToInt(type: Transaction.TransactionType): Int {
        return type.ordinal
    }

    @TypeConverter
    fun intToTransactionType(type: Int): Transaction.TransactionType {
        return Transaction.TransactionType.values()[type]
    }
}
*/

class ListStringConverter {
    @TypeConverter
    fun stringToTokenPairType(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {
        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun tokenPairTypeToString(list: List<String>): String {
        return Gson().toJson(list)
    }
}