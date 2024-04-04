package com.example.receipts.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.receipts.model.IngredientConverter
import com.example.receipts.model.RecipeEntity


@Database(entities = [RecipeEntity::class], version = 3, exportSchema = false)
@TypeConverters(IngredientConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        private var database: RecipeDatabase? = null
        private const val DATABASE_NAME = "recipe_app"

        @Synchronized
        fun getInstance(context: Context): RecipeDatabase {
            if (database == null) {
                database = databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return database!!
        }
    }
}

