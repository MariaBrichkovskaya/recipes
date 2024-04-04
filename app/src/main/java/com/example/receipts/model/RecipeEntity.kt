package com.example.receipts.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "recipes")
data class RecipeEntity(
    @ColumnInfo(name = "uri") val uri: String,
    @PrimaryKey @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "calories") val calories: Double,
    @ColumnInfo(name = "source") val source: String,
    @ColumnInfo(name = "totalTime") val totalTime: String,
    @ColumnInfo(name = "ingredients") val ingredients: ArrayList<Ingredient>
) : Serializable

