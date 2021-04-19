package com.example.cashappstocks.network.api

import com.example.cashappstocks.network.domain.CashStocksResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CashStocksAPI {
    @GET("cash-homework/cash-stocks-api/{jsonType}")
    fun getCashStocksResponse(@Path("jsonType") jsonType: String): Call<CashStocksResponse>
}