package com.example.cashappstocks.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.cashappstocks.TestCoroutineRule
import com.example.cashappstocks.network.domain.CashStocksResponse
import com.example.cashappstocks.network.domain.CashStocksResponseResult
import com.example.cashappstocks.network.domain.Stock
import com.example.cashappstocks.repository.CashStocksRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class StocksPortfolioViewModelTest {

    @get:Rule
    internal val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = TestCoroutineRule()

    private lateinit var viewModel: StocksPortfolioViewModel
    private var repo = mock(CashStocksRepository::class.java)
    private lateinit var mockObserver: Observer<MutableList<Stock>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = StocksPortfolioViewModel(repo)
        mockObserver = mock(Observer::class.java) as Observer<MutableList<Stock>>
    }

    @Test
    fun getDoorDashStoreFeed() = coroutineTestRule.runBlockingTest {
        val store = Stock(
            "SQ", "Square", "USD", 27079, "9"
        )
        val stores = mutableListOf(store)
        `when`(repo.getCashStocksResponse(anyString())).thenReturn(CashStocksResponseResult.Success(
            CashStocksResponse( stores)
        ))
        viewModel.cashStocksLiveData.observeForever(mockObserver)
        viewModel.getStockDetails("")
        assertEquals(stores, viewModel.cashStocksLiveData.value)
    }
}