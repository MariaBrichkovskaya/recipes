package com.example.receipts.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.receipts.service.RecipeRepository

class RecipeViewModelFactory(private val repository: RecipeRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            RecipeViewModel(
                this.repository,
                search = "",
                mealType = listOf(),
                cuisineType = listOf(),
                calories = "",
                time = ""
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
