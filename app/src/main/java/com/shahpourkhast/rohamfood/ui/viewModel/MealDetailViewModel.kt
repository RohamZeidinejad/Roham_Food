package com.shahpourkhast.rohamfood.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.data.repository.HomeRepository
import kotlinx.coroutines.launch

class MealDetailViewModel(private val foodsDatabase: FoodsDatabase) : ViewModel() {

    private val repository = HomeRepository()

    //-----------------------------------------------------------------------------

    private val _mealData = MutableLiveData<HomeFoodsData>()
    val mealData: LiveData<HomeFoodsData> = _mealData

    //-----------------------------------------------------------------------------

    fun getMealDetail(id: String) = viewModelScope.launch {

        try {

            val response = repository.getMealDetail(id)
            if (response.isSuccessful) {

                _mealData.postValue(response.body())

            }

        } catch (e: Exception) {

            Log.v("getMealDetailError", e.message.toString())

        }

    }

    //-----------------------------------------------------------------------------

    fun insertfoodDb(food: HomeFoodsData.Meal) = viewModelScope.launch {

        foodsDatabase.foodsDao.upsertFoodDb(food)

    }

    //-----------------------------------------------------------------------------

    fun deletefoodDb(food: HomeFoodsData.Meal) = viewModelScope.launch {

        foodsDatabase.foodsDao.deleteFoodDb(food)

    }

    //-----------------------------------------------------------------------------

    fun observeMealInFavorites(id: String) = foodsDatabase.foodsDao.observeFoodById(id)



}