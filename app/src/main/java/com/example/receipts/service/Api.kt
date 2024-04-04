package com.example.receipts.service

import com.example.receipts.model.Hit
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("recipes/v2")
    suspend fun getAllRecipes(
        @Query("type") type: String,
        @Query("q") q: String = "salmon",
        @Query("app_id") app_id: String = "a03c43af",
        @Query("app_key") app_key: String = "e5d79dffe2b66474ff75017f1e0f357d",
        @Query("ingr") numOfIngredients: String,
        @Query("time") time: String,
        @Query("calories") calories: String,
        @Query("mealType") mealType: List<String>,
        @Query("cuisineType") cuisineType: List<String>
    ): Response<Hit>
}
