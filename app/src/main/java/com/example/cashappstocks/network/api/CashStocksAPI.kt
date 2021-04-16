package com.example.cashappstocks.network.api

import com.example.cashappstocks.network.domain.CashStocksResponse
import retrofit2.Call
import retrofit2.http.GET

interface CashStocksAPI {

    @GET("cash-homework/cash-stocks-api")
    fun geCashStocksResponse(): Call<CashStocksResponse>

}