package com.shahpourkhast.rohamfood.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HomeFoodsData.Meal::class], version = 1, exportSchema = true)
abstract class FoodsDatabase() : RoomDatabase() {

    abstract val foodsDao : FoodsDao

    companion object {

        @Volatile
        private var database: FoodsDatabase? = null
        @Synchronized
        fun getDatabase(context: Context): FoodsDatabase {

            if (database == null) {

                database = Room
                    .databaseBuilder(context.applicationContext, FoodsDatabase::class.java, "foods.db")
                    .build()
            }

            return database!!

        }

    }
}