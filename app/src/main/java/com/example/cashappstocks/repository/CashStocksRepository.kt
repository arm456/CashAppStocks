package com.example.cashappstocks.repository

import com.example.cashappstocks.network.api.CashStocksAPI
import com.example.cashappstocks.network.domain.CashStocksResponseResult
import com.example.cashappstocks.network.domain.NetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

interface CashStocksRepository {
    suspend fun getCashStocksResponse(jsonType: String): CashStocksResponseResult
}

class CashStocksRepositoryImpl @Inject constructor(private val cashStocksAPI: CashStocksAPI) :
    CashStocksRepository {

    override suspend fun getCashStocksResponse(jsonType: String): CashStocksResponseResult {
        return try {
            withContext(Dispatchers.IO) {
                val stocksResponse = cashStocksAPI.geCashStocksResponse(jsonType).execute()
                if (stocksResponse.isSuccessful && stocksResponse.body() != null) {
                    CashStocksResponseResult.Success(stocksResponse.body())
                } else {
                    CashStocksResponseResult.Failure(
                        NetworkException(
                            stocksResponse.errorBody()?.string()
                        )
                    )
                }
            }
        } catch (e: Exception) {
            CashStocksResponseResult.Failure(exception = NetworkException(e.message))
        }
    }

}