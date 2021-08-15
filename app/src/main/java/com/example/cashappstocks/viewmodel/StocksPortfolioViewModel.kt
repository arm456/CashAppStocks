package com.example.cashappstocks.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.example.cashappstocks.network.domain.CashStocksResponseResult
import com.example.cashappstocks.network.domain.NetworkException
import com.example.cashappstocks.network.domain.Stock
import com.example.cashappstocks.repository.CashStocksRepository
import kotlinx.coroutines.launch

class StocksPortfolioViewModel(
    private val repository: CashStocksRepository,
    private val context: Context?
) : ViewModel() {

    private val _cashStocksLiveData = MutableLiveData<MutableList<Stock>>()
    val cashStocksLiveData: LiveData<MutableList<Stock>> = _cashStocksLiveData

    private val _errorLiveData = MutableLiveData<NetworkException>()
    val errorLiveData: LiveData<NetworkException> = _errorLiveData

    fun getStockDetails(jsonType: String) {
        viewModelScope.launch {
            jsonType.let {
                getStocks(jsonType)
            }
        }
    }

    private suspend fun getStocks(jsonType: String) {
        val dbres = this.context?.let { repository.getStocksFromDB(context = it) }
        if (dbres.isNullOrEmpty()) {
            repository.getCashStocksResponse(jsonType).let { cashStocksResult ->
                when (cashStocksResult) {
                    is CashStocksResponseResult.Success -> {
                        _cashStocksLiveData.postValue(
                            _cashStocksLiveData.value.apply {
                                cashStocksResult.stocksFeed?.stocks?.let {
                                    this?.addAll(it)
                                    context?.let { it1 -> repository.insertStocksIntoDB(it1, it) }
                                }
                            } ?: run { cashStocksResult.stocksFeed?.stocks?.toMutableList() }
                        )
                    }
                    is CashStocksResponseResult.Failure -> {
                        _errorLiveData.postValue(cashStocksResult.exception)
                    }
                }
            }
        } else {
            _cashStocksLiveData.postValue(
                _cashStocksLiveData.value.apply {
                    this?.addAll(dbres)
                }?: run { dbres.toMutableList() }
            )
        }
    }

    class Factory(private val repository: CashStocksRepository, private val context: Context?) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StocksPortfolioViewModel(repository, context) as T
        }
    }
}