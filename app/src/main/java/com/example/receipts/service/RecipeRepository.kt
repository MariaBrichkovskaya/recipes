package com.example.receipts.service

class RecipeRepository(private val retrofitService: RetrofitService) {
    suspend fun getList() = retrofitService.getAllRecipes()
}
