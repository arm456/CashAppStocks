package com.example.cashappstocks.network.domain

/**
 * Sealed class for Success, failure & Loading results for DoorDash store feed
 */
sealed class CashStocksResponseResult {
    data class Success(val stocksFeed: CashStocksResponse?) : CashStocksResponseResult()
    data class Failure(val exception: NetworkException) : CashStocksResponseResult()
}

data class NetworkException(
    val errorMessage: String?
) : Exception()