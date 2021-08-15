package com.example.cashappstocks.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cashappstocks.network.domain.Stock

@Dao
interface StocksDao {

    @Insert
    fun insert(stocksList: List<Stock>?)

    @Update
    fun update(stock: Stock)

    @Query("SELECT * from stock_table")
    fun get(): List<Stock>?
}