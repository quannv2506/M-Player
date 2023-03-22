package com.project.mvvm.db.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.project.mvvm.utilities.formatUsd
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Entity(tableName = "tokens")
@Parcelize
data class Token(
    @NonNull
    @PrimaryKey
    @SerializedName("primary_key")
    val primaryKey: String = "",
    @SerializedName("chain_id")
    val chainId: String = "",
    @SerializedName("decimals")
    val decimals: Int = 0,
    @SerializedName("address")
    val address: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("symbol")
    val symbol: String = "",
    @SerializedName("symbol_chain")
    val symbolChain: String = "",
    @SerializedName("swap")
    val swap: Boolean = false,
    @SerializedName("logo")
    val logo: String = "",
    @SerializedName("price")
    val price: BigDecimal = BigDecimal.ZERO,
    @SerializedName("market_cap")
    val market_cap: BigDecimal = BigDecimal.ZERO,
    @SerializedName("buy")
    val buy: Boolean = false,
) : Parcelable {

    val displayPrice: String
        get() = StringBuffer("$").append(price.formatUsd(8)).toString()
}

