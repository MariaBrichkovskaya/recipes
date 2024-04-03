package com.example.receipts.service

class RecipeRepository constructor(private val retrofitService: RetrofitService) {
    suspend fun getList() = retrofitService.getAllRecipes()
}
