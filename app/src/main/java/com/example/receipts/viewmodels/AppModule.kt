package com.example.receipts.di

import android.content.Context
import androidx.room.Room
import com.example.receipts.db.RecipeDao
import com.example.receipts.db.RecipeDatabase
import com.example.receipts.service.RecipeRepository
import com.example.receipts.viewmodels.RecipeFilterParams
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            "recipe_app"
        ).build()
    }

    @Provides
    fun provideRecipeDao(database: RecipeDatabase): RecipeDao {
        return database.recipeDao()
    }

    @Provides
    fun provideFilterParams(): RecipeFilterParams {
        return RecipeFilterParams(
            search = "",
            mealType = listOf(),
            cuisineType = listOf(),
            calories = "",
            time = ""
        )
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(): RecipeRepository {
        return RecipeRepository()
    }
}
