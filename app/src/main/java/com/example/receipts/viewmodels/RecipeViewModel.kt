package com.example.receipts.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.receipts.model.Hit
import com.example.receipts.model.Recipe
import com.example.receipts.model.RecipeCollections
import com.example.receipts.service.RecipeRepository
import com.example.receipts.service.RetrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel(private val recipeRepository: RecipeRepository): ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val recipesList = MutableLiveData<List<Recipe>>()
    val loading = MutableLiveData<Boolean>()
    private var job: Job? = null

    fun getAllRecipes() {
        job = CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = recipeRepository.getList()
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


