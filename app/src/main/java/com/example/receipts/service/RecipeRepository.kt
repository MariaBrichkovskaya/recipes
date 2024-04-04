package com.example.receipts.service

class RecipeRepository {
    suspend fun getList(
        searchingString: String,
        time: String,
        calories: String,
        mealType: List<String>,
        cuisineType: List<String>
    ) =
        RetrofitService.instance.getAllRecipes(
            "public",
            q = searchingString,
            calories = calories,
            time = time,
            mealType = mealType,
            cuisineType = cuisineType
        )
}
