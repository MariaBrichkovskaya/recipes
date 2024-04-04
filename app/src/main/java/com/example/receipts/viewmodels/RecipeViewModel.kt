package com.example.receipts.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.receipts.model.Recipe
import com.example.receipts.service.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeViewModel(private val recipeRepository: RecipeRepository, var search: String) :
    ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val recipesList = MutableLiveData<List<Recipe>>()
    private val loading = MutableLiveData<Boolean>()
    private var job: Job? = null

    fun getAllRecipes() {
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = recipeRepository.getList()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val filteredRecipes = response.body()?.hits?.map { it.recipe }
                            ?.filter { it.label.lowercase().contains(search.lowercase()) }
                        recipesList.postValue(filteredRecipes ?: listOf())
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


