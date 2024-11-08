package com.example.receipts.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.receipts.model.RecipeEntity
import javax.inject.Singleton

@Dao
@Singleton
interface RecipeDao {
    @Query("SELECT * FROM recipes WHERE label = :label")
    fun getByLabel(label: String): LiveData<RecipeEntity?>


    @Query("SELECT * FROM recipes")
    fun getAll(): List<RecipeEntity>

    @Insert
    fun insert(recipe: RecipeEntity)

    @Delete
    fun delete(recipe: RecipeEntity)
}
