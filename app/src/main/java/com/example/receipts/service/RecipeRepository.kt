package com.example.receipts.service

import com.example.receipts.model.Hit
import retrofit2.Response
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val api: Api) {
    suspend fun getList(
        searchingString: String,
        time: String,
        calories: String,
        mealType: List<String>,
        cuisineType: List<String>
    ): Response<Hit> {
        return api.getAllRecipes(
            type = "public",
            q = searchingString,
            time = time,
            calories = calories,
            mealType = mealType,
            cuisineType = cuisineType
        )
    }
}


