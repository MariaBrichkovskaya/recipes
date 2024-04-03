package com.example.receipts.response

import com.example.receipts.model.Recipe

data class RecipeResponse(
    val hits: List<Recipe>
)

