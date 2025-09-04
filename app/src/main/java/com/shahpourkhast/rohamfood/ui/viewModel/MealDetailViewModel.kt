package com.shahpourkhast.rohamfood.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.data.repository.FavoritesRepository
import com.shahpourkhast.rohamfood.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val favoritesRepository: FavoritesRepository) : ViewModel() {


    //-----------------------------------------------------------------------------

    private val _mealData = MutableStateFlow<HomeFoodsData?>(null)
    val mealData: StateFlow<HomeFoodsData?> = _mealData.asStateFlow()

    //-----------------------------------------------------------------------------

    fun getMealDetail(id: String) = viewModelScope.launch {

        try {

            val response = repository.getMealDetail(id)
            if (response.isSuccessful) _mealData.value = response.body()

        } catch (e: Exception) {

            Log.v("getMealDetailError", e.message.toString())

        }

    }

    //-----------------------------------------------------------------------------

    fun insertfoodDb(food: HomeFoodsData.Meal) = viewModelScope.launch {

      favoritesRepository.upsert(food)

    }

    //-----------------------------------------------------------------------------

    fun deletefoodDb(food: HomeFoodsData.Meal) = viewModelScope.launch {

        favoritesRepository.delete(food)

    }

    //-----------------------------------------------------------------------------

    fun observeMealInFavorites(id: String) = favoritesRepository.observeById(id)



}