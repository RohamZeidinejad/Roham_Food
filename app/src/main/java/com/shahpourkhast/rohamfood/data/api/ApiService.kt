package com.shahpourkhast.rohamfood.data.api

import com.shahpourkhast.rohamfood.data.dataClass.CategoriesData
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    suspend fun getLetsMakeThis(): Response<HomeFoodsData>

    //--------------------------------------------------------------------

    @GET("lookup.php?")
    suspend fun getLetsMakeThisDetail(@Query("i") id: String): Response<HomeFoodsData>

    //--------------------------------------------------------------------

    @GET("filter.php?")
    suspend fun getSeaFoods(@Query("c") categoryName: String): Response<HomeFoodsData>

    //--------------------------------------------------------------------

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoriesData>

    //--------------------------------------------------------------------

    @GET("filter.php?")
    suspend fun getCategoryFoods(@Query("c") categoryName: String): Response<HomeFoodsData>

    //--------------------------------------------------------------------

    @GET("search.php?")
    suspend fun searchFoods(@Query("s") searchQuery: String): Response<HomeFoodsData>

}