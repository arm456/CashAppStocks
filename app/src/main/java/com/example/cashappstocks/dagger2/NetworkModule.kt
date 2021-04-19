package com.example.cashappstocks.dagger2

import com.example.cashappstocks.network.api.CashStocksAPI
import com.example.cashappstocks.repository.CashStocksRepository
import com.example.cashappstocks.repository.CashStocksRepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    /**
     * Provides the CashStocksRepository service implementation.
     * @param api the CashStocksAPI object used to instantiate the repository
     * @return the CashStocksRepository implementation.
     */
    @Provides
    fun getCashStocksRepository(api: CashStocksAPI): CashStocksRepository =
        CashStocksRepositoryImpl(api)

    /**
     * Provides the CashStocksAPI service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the CashStocksAPI service implementation.
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
    internal fun getRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /**
     * Provides the GSON object.
     * @return the GSON object
     */
    @Provides
    internal fun getGSON(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
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