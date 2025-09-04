package com.shahpourkhast.rohamfood.ui.viewModel

import androidx.lifecycle.ViewModel
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CategoryFoodsState {
    data object Idle : CategoryFoodsState
    data object Loading : CategoryFoodsState
    data class Success(val data: HomeFoodsData) : CategoryFoodsState
    data class Error(val message: String) : CategoryFoodsState
}

@HiltViewModel
class CategoryFoodsViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    //-----------------------------------------------------------------------------

    private val _categoryFoods = MutableStateFlow<CategoryFoodsState>(CategoryFoodsState.Idle)
    val categoryFoods: StateFlow<CategoryFoodsState> = _categoryFoods.asStateFlow()

    //-----------------------------------------------------------------------------

    fun getCategoryFoods(categoryName: String) = viewModelScope.launch {

        _categoryFoods.value = CategoryFoodsState.Loading

        try {

            val response = repository.getCategoryFoods(categoryName)
            val body = response.body()
            if (response.isSuccessful && body != null) {

                _categoryFoods.value = CategoryFoodsState.Success(body)

            } else _categoryFoods.value = CategoryFoodsState.Error(response.message().ifBlank { "No Data!" })


        } catch (e: Exception) {

            _categoryFoods.value = CategoryFoodsState.Error(e.message ?: "Unknown Error!")

        }

    }

}