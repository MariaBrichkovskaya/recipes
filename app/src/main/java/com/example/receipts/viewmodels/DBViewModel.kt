package com.example.receipts.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receipts.db.RecipeDao
import com.example.receipts.model.RecipeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DBViewModel @Inject constructor(private val recipeDao: RecipeDao) : ViewModel() {
    val recipesList = MutableLiveData<List<RecipeEntity>>()
    var recipeLiveData: LiveData<RecipeEntity?> = MutableLiveData()

    fun getAllRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            recipesList.postValue(recipeDao.getAll())
        }
    }

    fun insert(dbItem: RecipeEntity) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            recipeDao.insert(dbItem)
        }
    }

    fun delete(recipeEntity: RecipeEntity) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            recipeDao.delete(recipeEntity)
        }
    }

    fun getByLabel(label: String) {
        viewModelScope.launch {
            recipeLiveData = recipeDao.getByLabel(label)
        }
    }

}
