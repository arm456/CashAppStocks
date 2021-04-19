package com.example.cashappstocks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cashappstocks.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val stockOptionsFragment = CashStockOptionsFragment(R.layout.fragment_stocks_options)
            supportFragmentManager.beginTransaction().replace(R.id.container, stockOptionsFragment).commit()
        }
    }
}