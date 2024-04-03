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

class RecipeViewModel(private val recipeRepository: RecipeRepository): ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val recipesList = MutableLiveData<List<Recipe>>()
    val loading = MutableLiveData<Boolean>()

    private var job: Job? = null

    fun getAllRecipes(){
        job= CoroutineScope(Dispatchers.IO).launch {
            val response =recipeRepository.getList()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    recipesList.postValue(response.body())
                    loading.value = false
                }else{
                    OnError("Error :  ${response.message()}")

                }
            }
        }

    }
    private fun OnError(message: String) {
        errorMessage.value = message
        loading.value = false

    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
