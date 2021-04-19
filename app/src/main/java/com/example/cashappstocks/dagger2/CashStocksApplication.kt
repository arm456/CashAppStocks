package com.example.cashappstocks.dagger2

import android.app.Application

class CashStocksApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}