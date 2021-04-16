package com.example.cashappstocks.dagger2

import com.example.cashappstocks.network.api.CashStocksAPI
import com.example.cashappstocks.repository.CashStocksRepository
import com.example.cashappstocks.repository.CashStocksRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun getCashStocksRepository(service: CashStocksAPI): CashStocksRepository =
        CashStocksRepositoryImpl(service)

    /**
     * Provides the DoorDashAPI service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the DoorDashAPI service implementation.
     */
    @Provides
    internal fun getCashStocksAPI(retrofit: Retrofit): CashStocksAPI {
        return retrofit.create(CashStocksAPI::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    internal fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides the OkHttpClient object.
     * @return the OkHttpClient object
     */
    @Provides
    internal fun getOkHttClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    companion object {
        private const val BASE_URL = "https://storage.googleapis.com/"
    }
}