package com.shahpourkhast.rohamfood.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.data.repository.HomeRepository
import kotlinx.coroutines.launch

class CategoryFoodsViewModel : ViewModel() {

    private val repository = HomeRepository()

    //-----------------------------------------------------------------------------

    private val _categoryFoods = MutableLiveData<HomeFoodsData>()
    val categoryFoods: LiveData<HomeFoodsData> = _categoryFoods

    //-----------------------------------------------------------------------------

    fun getCategoryFoods(categoryName: String) = viewModelScope.launch {

        try {

            val response = repository.getCategoryFoods(categoryName)
            if (response.isSuccessful) _categoryFoods.postValue(response.body())

        } catch (e: Exception) {

            Log.v("getCategoryFoodsError", e.message.toString())

        }

    }

}