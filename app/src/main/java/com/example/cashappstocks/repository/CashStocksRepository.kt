package com.example.cashappstocks.repository

import android.content.Context
import com.example.cashappstocks.db.StocksDatabase
import com.example.cashappstocks.network.api.CashStocksAPI
import com.example.cashappstocks.network.domain.CashStocksResponseResult
import com.example.cashappstocks.network.domain.NetworkException
import com.example.cashappstocks.network.domain.Stock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

interface CashStocksRepository {
    suspend fun getCashStocksResponse(jsonType: String): CashStocksResponseResult

    suspend fun getStocksFromDB(context: Context): List<Stock>?

    suspend fun insertStocksIntoDB(context: Context, listStocks: List<Stock>?)
}

class CashStocksRepositoryImpl @Inject constructor(private val cashStocksAPI: CashStocksAPI) :
    CashStocksRepository {

    override suspend fun getCashStocksResponse(jsonType: String): CashStocksResponseResult {
        return try {
            withContext(Dispatchers.IO) {
                val stocksResponse = cashStocksAPI.getCashStocksResponse(jsonType).execute()
                if (stocksResponse.isSuccessful && stocksResponse.body() != null) {
                    CashStocksResponseResult.Success(stocksResponse.body())
                } else {
                    CashStocksResponseResult.Failure(
                        NetworkException(stocksResponse.errorBody()?.string())
                    )
                }
            }
        } catch (e: Exception) {
            CashStocksResponseResult.Failure(exception = NetworkException(e.message))
        }
    }

    override suspend fun getStocksFromDB(context: Context): List<Stock>? {
        return try {
            withContext(Dispatchers.IO) {
                StocksDatabase.getInstance(context).sleepDatabaseDao.get()
            }
        } catch (e: Exception) {
            TODO()
        }
    }

    override suspend fun insertStocksIntoDB(context: Context, listStocks: List<Stock>?) {
        return try {
            withContext(Dispatchers.IO) {
                StocksDatabase.getInstance(context).sleepDatabaseDao.insert(listStocks)
            }
        } catch (e: Exception) {
            TODO()
        }
    }

}