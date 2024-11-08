package com.example.receipts.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.receipts.model.Recipe
import com.example.receipts.service.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    val filterParams: RecipeFilterParams
) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val recipesList = MutableLiveData<List<Recipe>>()
    private val loading = MutableLiveData<Boolean>()
    private var job: Job? = null

    fun getAllRecipes() {
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                if (filterParams.search.isEmpty()) filterParams.search = ""
                if (filterParams.mealType.isEmpty()) filterParams.mealType =
                    listOf("Dinner", "Lunch", "Breakfast", "Snack", "Teatime")
                if (filterParams.cuisineType.isEmpty()) filterParams.cuisineType = listOf(
                    "American", "Asian", "British", "Caribbean", "Central Europe", "Chinese",
                    "Eastern Europe", "French", "Indian", "Italian", "Japanese", "Kosher",
                    "Mediterranean", "Mexican", "Middle Eastern", "Nordic", "South American",
                    "South East Asian"
                )
                if (filterParams.time.isEmpty()) filterParams.time = "1+"
                if (filterParams.calories.isEmpty()) filterParams.calories = "1+"
                val response = recipeRepository.getList(
                    searchingString = filterParams.search.lowercase(),
                    mealType = filterParams.mealType,
                    cuisineType = filterParams.cuisineType,
                    time = filterParams.time,
                    calories = filterParams.calories
                )
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        recipesList.postValue(response.body()?.hits?.map { it.recipe })
                        loading.value = false
                    } else {
                        onError("Error : ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                onError("Exception: ${e.message}")
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

data class RecipeFilterParams(
    var search: String,
    var mealType: List<String>,
    var cuisineType: List<String>,
    var time: String,
    var calories: String
)

