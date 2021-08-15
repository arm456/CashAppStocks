package com.example.cashappstocks.network.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CashStocksResponse(
    @Expose val stocks: List<Stock>
)

@Entity(tableName = "stock_table")
data class Stock(

    @PrimaryKey
    @SerializedName("ticker") val ticker: String,

    @ColumnInfo(name = "stock_name")
    @SerializedName("name") val name: String,

    @ColumnInfo(name = "stock_currency")
    @SerializedName("currency") val currency: String,

    @ColumnInfo(name = "stock_current_price")
    @SerializedName("current_price_cents") val currentPriceCents: Long,

    @ColumnInfo(name = "stock_quantity")
    @SerializedName("quantity") val quantity: Int?
)