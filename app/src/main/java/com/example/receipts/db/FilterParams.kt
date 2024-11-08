package com.example.receipts.db

class RecipeFilterParams(
    var search: String,
    var mealType: List<String>,
    var cuisineType: List<String>,
    var time: String,
    var calories: String
)