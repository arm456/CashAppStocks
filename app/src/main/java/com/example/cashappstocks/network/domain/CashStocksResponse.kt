package com.example.cashappstocks.network.domain

import com.google.gson.annotations.SerializedName

data class CashStocksResponse(
    @SerializedName("ticker") val ticker: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("currency") val currency: String?,
    @SerializedName("current_price_cents") val currentPriceCents: Int?,
    @SerializedName("quantity") val quantity: Int?
)