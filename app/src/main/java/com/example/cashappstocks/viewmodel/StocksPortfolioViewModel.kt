package com.example.cashappstocks.viewmodel

import androidx.lifecycle.*
import com.example.cashappstocks.network.domain.CashStocksResponseResult
import com.example.cashappstocks.network.domain.NetworkException
import com.example.cashappstocks.network.domain.Stock
import com.example.cashappstocks.repository.CashStocksRepository
import kotlinx.coroutines.launch

class StocksPortfolioViewModel(private val repository: CashStocksRepository) : ViewModel() {

    private val _cashStocksLiveData = MutableLiveData<MutableList<Stock>>()
    val cashStocksLiveData: LiveData<MutableList<Stock>> = _cashStocksLiveData

    private val _errorLiveData = MutableLiveData<NetworkException>()
    val errorLiveData: LiveData<NetworkException> = _errorLiveData

    fun getStockDetails(jsonType: String) {
        viewModelScope.launch {
            jsonType.let {
                repository.getCashStocksResponse(jsonType).let { cashStocksResult ->
                    when (cashStocksResult) {
                        is CashStocksResponseResult.Success -> {
                            _cashStocksLiveData.postValue(
                                _cashStocksLiveData.value.apply {
                                    cashStocksResult.stocksFeed?.stocks?.let { this?.addAll(it) }
                                } ?: run { cashStocksResult.stocksFeed?.stocks?.toMutableList() }
                            )
                        }
                        is CashStocksResponseResult.Failure -> {
                            _errorLiveData.postValue(cashStocksResult.exception)
                        }
                    }
                }
            }
        }
    }

    class Factory(private val repository: CashStocksRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StocksPortfolioViewModel(repository) as T
        }
    }
}