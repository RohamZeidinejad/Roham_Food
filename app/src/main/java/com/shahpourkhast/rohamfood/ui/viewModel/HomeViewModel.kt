package com.shahpourkhast.rohamfood.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahpourkhast.rohamfood.data.dataClass.CategoriesData
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.data.repository.HomeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(private val foodsDatabase: FoodsDatabase) : ViewModel() {

    private val repository = HomeRepository()

    //-----------------------------------------------------------------------------

    private val _letsMakeThis = MutableLiveData<HomeFoodsData>()
    val letsMakeThis: LiveData<HomeFoodsData> = _letsMakeThis

    //-----------------------------------------------------------------------------

    private val _seaFoods = MutableLiveData<HomeFoodsData>()
    val seaFoods: LiveData<HomeFoodsData> = _seaFoods

    //-----------------------------------------------------------------------------

    private val _categories = MutableLiveData<CategoriesData>()
    val categories: LiveData<CategoriesData> = _categories

    //-----------------------------------------------------------------------------

    private val _searchFoods = MutableLiveData<HomeFoodsData?>()
    val searchFoods: LiveData<HomeFoodsData?> = _searchFoods

    //-----------------------------------------------------------------------------

    init {

        getLetsMakeThis()

        getSeaFoods("Seafood")

        getCategories()

    }

    //-----------------------------------------------------------------------------

    private fun getLetsMakeThis() = viewModelScope.launch {

        try {

            val response = repository.getLetsMakeThis()
            if (response.isSuccessful) _letsMakeThis.postValue(response.body())


        } catch (e: Exception) {

            Log.v("getLetsMakeThisError", e.message.toString())

        }
    }

    //-----------------------------------------------------------------------------

    private fun getSeaFoods(categoryName: String) = viewModelScope.launch {

        try {

            val response = repository.getSeaFoods(categoryName)
            if (response.isSuccessful) _seaFoods.postValue(response.body())

        } catch (e: Exception) {

            Log.v("getSeaFoodError", e.message.toString())

        }

    }

    //-----------------------------------------------------------------------------

    private fun getCategories() = viewModelScope.launch {

        try {

            val response = repository.getCategories()
            if (response.isSuccessful) _categories.postValue(response.body())


        } catch (e: Exception) {

            Log.v("getCategoriesError", e.message.toString())

        }

    }

    //-----------------------------------------------------------------------------

    fun getFavoriteFoodsDb() = foodsDatabase.foodsDao.getFavoriteFoodsDb()

    //-----------------------------------------------------------------------------

    var searchJob: Job? = null

    fun searchFoods(searchQuery: String) {


        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)

            if (searchQuery.isEmpty()) {

                _searchFoods.postValue(null)
                return@launch

            }


            try {

                val response = repository.searchFoods(searchQuery)
                if (response.isSuccessful) _searchFoods.postValue(response.body())


            } catch (e: Exception) {

                Log.v("searchFoodsError", e.message.toString())

            }

        }

    }


}