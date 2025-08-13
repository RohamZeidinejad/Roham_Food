package com.shahpourkhast.rohamfood.data.repository

import com.shahpourkhast.rohamgoft.data.api.RetrofitInstance

class HomeRepository() {

    suspend fun getLetsMakeThis() = RetrofitInstance.api.getLetsMakeThis()

    //--------------------------------------------------------------------------------------

    suspend fun getMealDetail(id: String) = RetrofitInstance.api.getLetsMakeThisDetail(id)

    //--------------------------------------------------------------------------------------

    suspend fun getSeaFoods(categoryName: String) = RetrofitInstance.api.getSeaFoods(categoryName)

    //--------------------------------------------------------------------------------------

    suspend fun getCategories() = RetrofitInstance.api.getCategories()

    //--------------------------------------------------------------------------------------

    suspend fun getCategoryFoods(categoryName: String) = RetrofitInstance.api.getCategoryFoods(categoryName)

    //--------------------------------------------------------------------------------------

    suspend fun searchFoods(searchQuery: String) = RetrofitInstance.api.searchFoods(searchQuery)


}