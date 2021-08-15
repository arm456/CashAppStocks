package com.example.cashappstocks.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cashappstocks.network.domain.Stock

@Database(entities = [Stock::class], version = 1, exportSchema = false)
abstract class StocksDatabase : RoomDatabase() {
    abstract val sleepDatabaseDao: StocksDao

    companion object {

        @Volatile
        private var INSTANCE: StocksDatabase? = null

        fun getInstance(context: Context): StocksDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StocksDatabase::class.java,
                        "stock_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}