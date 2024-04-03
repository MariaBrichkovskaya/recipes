package com.example.receipts.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val uri: String,
    val label: String,
    val image: String,
    val calories:Double,
    val source: String,
    val totalTime: String,
    val ingredients: ArrayList<Ingredient>
) : Parcelable

