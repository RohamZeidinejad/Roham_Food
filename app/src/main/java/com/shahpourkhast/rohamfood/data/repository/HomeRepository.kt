package com.shahpourkhast.rohamfood.data.repository

import com.shahpourkhast.rohamfood.data.api.ApiService
import com.shahpourkhast.rohamfood.data.database.FoodsDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val api: ApiService) {

    suspend fun getLetsMakeThis() = api.getLetsMakeThis()

    //--------------------------------------------------------------------------------------

    suspend fun getMealDetail(id: String) = api.getLetsMakeThisDetail(id)

    //--------------------------------------------------------------------------------------

    suspend fun getSeaFoods(categoryName: String) = api.getSeaFoods(categoryName)

    //--------------------------------------------------------------------------------------

    suspend fun getCategories() = api.getCategories()

    //--------------------------------------------------------------------------------------

    suspend fun getCategoryFoods(categoryName: String) = api.getCategoryFoods(categoryName)

    //--------------------------------------------------------------------------------------

    suspend fun searchFoods(searchQuery: String) = api.searchFoods(searchQuery)


}