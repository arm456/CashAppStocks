package com.example.cashappstocks.network.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CashStocksResponse(
    @Expose val stocks: List<Stock>
)

data class Stock(
    @SerializedName("ticker") val ticker: String,
    @SerializedName("name") val name: String,
    @SerializedName("currency") val currency: String,
    @SerializedName("current_price_cents") val currentPriceCents: Long,
    @SerializedName("quantity") val quantity: Int?
)