package com.example.receipts.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object IngredientConverter {
    @TypeConverter
    fun arrayListToString(list: ArrayList<Ingredient?>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToArrayList(json: String?): ArrayList<Ingredient> {
        return Gson().fromJson(json, object : TypeToken<ArrayList<Ingredient?>?>() {}.type)
    }
}