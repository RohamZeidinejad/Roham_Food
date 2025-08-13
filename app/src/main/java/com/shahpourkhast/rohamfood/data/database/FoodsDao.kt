package com.shahpourkhast.rohamfood.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFoodDb(food: HomeFoodsData.Meal)

    //-----------------------------------------------------------------------------

    @Delete
    suspend fun deleteFoodDb(food: HomeFoodsData.Meal)

    //-----------------------------------------------------------------------------

    @Query("SELECT * FROM meals_table")
    fun getFavoriteFoodsDb() : LiveData<List<HomeFoodsData.Meal>>

    //-----------------------------------------------------------------------------

    @Query("SELECT * FROM meals_table WHERE idMeal = :id")
    fun observeFoodById(id: String): LiveData<HomeFoodsData.Meal?>

}