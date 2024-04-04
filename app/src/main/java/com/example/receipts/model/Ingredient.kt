package com.example.receipts.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val text: String,
    val weight: Double,
) : Parcelable

