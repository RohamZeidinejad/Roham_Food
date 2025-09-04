package com.shahpourkhast.rohamfood.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HomeFoodsData.Meal::class], version = 1, exportSchema = true)
abstract class FoodsDatabase : RoomDatabase() {

    abstract val foodsDao: FoodsDao

}
