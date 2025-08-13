package com.shahpourkhast.rohamfood.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase

class HomeViewModelFactory(private val foodsDatabase: FoodsDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(foodsDatabase) as T
    }

}