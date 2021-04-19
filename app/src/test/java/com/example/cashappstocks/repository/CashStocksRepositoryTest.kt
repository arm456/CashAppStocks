package com.example.cashappstocks.repository

import com.example.cashappstocks.network.api.CashStocksAPI
import com.example.cashappstocks.network.domain.CashStocksResponse
import com.example.cashappstocks.network.domain.CashStocksResponseResult
import com.example.cashappstocks.network.domain.Stock
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Response

class CashStocksRepositoryTest {
    private lateinit var stocksRepository: CashStocksRepository
    private var stocksAPI = Mockito.mock(CashStocksAPI::class.java)

    @Before
    fun setUp() {
        stocksRepository = CashStocksRepositoryImpl(stocksAPI)
    }

    @Test
    fun getDoorDashStoreFeedSuccess() = runBlocking {
        val store = Stock(
            "SQ", "Square", "USD", 27079, "9"
        )
        val responseList = listOf(store)
        Mockito.`when`(
            stocksAPI.getCashStocksResponse(ArgumentMatchers.anyString())
        ).thenReturn(
            Mockito.mock(Call::class.java) as Call<CashStocksResponse>
        )

        val call = stocksAPI.getCashStocksResponse("portfolio.json")
        Mockito.`when`(call.execute()).thenReturn(
            Response.success(CashStocksResponse(stocks = responseList))
        )

        val expectedStoreFeedResult: CashStocksResponseResult =
            stocksRepository.getCashStocksResponse("portfolio.json")
        assertTrue(expectedStoreFeedResult is CashStocksResponseResult.Success)
        val expectedSuccessResult = expectedStoreFeedResult as CashStocksResponseResult.Success
        assertEquals(expectedSuccessResult.stocksFeed?.stocks, responseList)
    }

    @Test
    fun getDoorDashStoreFeedError() = runBlocking {
        Mockito.`when`(
            stocksAPI.getCashStocksResponse(ArgumentMatchers.anyString())
        ).thenReturn(
            Mockito.mock(Call::class.java) as Call<CashStocksResponse>
        )

        val call = stocksAPI.getCashStocksResponse("portfolio_malformed.json")
        Mockito.`when`(call.execute()).thenReturn(
            Response.error(
                404, ResponseBody.create(
                    MediaType.parse("text/plain"), "No stocks found"
                )
            )
        )

        val expectedStoreFeedResult: CashStocksResponseResult =
            stocksRepository.getCashStocksResponse("portfolio_malformed.json")
        assertTrue(expectedStoreFeedResult is CashStocksResponseResult.Failure)
        val expectedErrorResult = expectedStoreFeedResult as CashStocksResponseResult.Failure
        assertEquals(expectedErrorResult.exception.errorMessage, "No stocks found")
    }

}