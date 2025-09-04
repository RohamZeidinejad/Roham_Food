package com.shahpourkhast.rohamfood.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahpourkhast.rohamfood.data.dataClass.CategoriesData
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.data.repository.FavoritesRepository
import com.shahpourkhast.rohamfood.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository ,
    private val favoritesRepository: FavoritesRepository) : ViewModel() {

    //-----------------------------------------------------------------------------

    private val _letsMakeThis = MutableStateFlow<HomeFoodsData?>(null)
    val letsMakeThis: StateFlow<HomeFoodsData?> = _letsMakeThis.asStateFlow()

    //-----------------------------------------------------------------------------

    private val _seaFoods = MutableStateFlow<HomeFoodsData?>(null)
    val seaFoods: StateFlow<HomeFoodsData?> = _seaFoods

    //-----------------------------------------------------------------------------

    private val _categories = MutableStateFlow<CategoriesData?>(null)
    val categories: StateFlow<CategoriesData?> = _categories

    //-----------------------------------------------------------------------------

    private val _searchFoods = MutableStateFlow<HomeFoodsData?>(null)
    val searchFoods: StateFlow<HomeFoodsData?> = _searchFoods

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
            if (response.isSuccessful) _letsMakeThis.value = response.body()


        } catch (e: Exception) {

            Log.v("getLetsMakeThisError", e.message.toString())

        }
    }

    //-----------------------------------------------------------------------------

    private fun getSeaFoods(categoryName: String) = viewModelScope.launch {

        try {

            val response = repository.getSeaFoods(categoryName)
            if (response.isSuccessful) _seaFoods.value = response.body()

        } catch (e: Exception) {

            Log.v("getSeaFoodError", e.message.toString())

        }

    }

    //-----------------------------------------------------------------------------

    private fun getCategories() = viewModelScope.launch {

        try {

            val response = repository.getCategories()
            if (response.isSuccessful) _categories.value = response.body()


        } catch (e: Exception) {

            Log.v("getCategoriesError", e.message.toString())

        }

    }

    //-----------------------------------------------------------------------------

    fun getFavoriteFoodsDb() = favoritesRepository.getAll()

    //-----------------------------------------------------------------------------

    var searchJob: Job? = null

    fun searchFoods(searchQuery: String) {


        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)

            if (searchQuery.isEmpty()) {

                _searchFoods.value = null
                return@launch

            }


            try {

                val response = repository.searchFoods(searchQuery)
                if (response.isSuccessful) _searchFoods.value = response.body()


            } catch (e: Exception) {

                Log.v("searchFoodsError", e.message.toString())

            }

        }

    }


}