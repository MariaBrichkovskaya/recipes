package com.example.receipts.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Hit(val hits: ArrayList<RecipeCollections>) : Parcelable
