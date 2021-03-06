package com.example.cashappstocks.dagger2

import android.content.Context
import com.example.cashappstocks.ui.CashStockOptionsFragment
import com.example.cashappstocks.ui.StocksPortfolioFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Component providing inject() methods.
 */
@Singleton
@Component(modules = [NetworkModule::class, AndroidInjectionModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(stockOptionsFragment: CashStockOptionsFragment)

    fun inject(stocksPortfolioFragment: StocksPortfolioFragment)
}