package com.shahpourkhast.rohamfood.data.repository

import com.shahpourkhast.rohamfood.data.database.FoodsDao
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(private val dao: FoodsDao) {

    suspend fun upsert(food: HomeFoodsData.Meal) = dao.upsertFoodDb(food)

    //--------------------------------------------------------------------------------------

    suspend fun delete(food: HomeFoodsData.Meal) = dao.deleteFoodDb(food)

    //--------------------------------------------------------------------------------------

    fun getAll(): Flow<List<HomeFoodsData.Meal>> = dao.getFavoriteFoodsDb()

    //--------------------------------------------------------------------------------------

    fun observeById(id: String): Flow<HomeFoodsData.Meal?> = dao.observeFoodById(id)

}
