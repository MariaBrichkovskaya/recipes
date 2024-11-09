package com.example.receipts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.receipts.model.Hit
import com.example.receipts.model.Recipe
import com.example.receipts.service.Api
import com.example.receipts.service.RecipeRepository
import com.example.receipts.service.RetrofitService
import com.example.receipts.viewmodels.RecipeFilterParams
import com.example.receipts.viewmodels.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After

import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RecipeRepositoryTest {

    private lateinit var recipeRepository: RecipeRepository

    @Mock
    private lateinit var api: Api

    @Before
    fun setUp() {
        recipeRepository = RecipeRepository(api)
    }

    @Test
    fun` getList should return successful response`() = runBlocking {
        val mockResponse = Response.success(Hit(arrayListOf()))
        val searchingString = "salmon"
        val time = "1+"
        val calories = "1+"
        val mealType = listOf("Dinner")
        val cuisineType = listOf("American")

        `when`(api.getAllRecipes(
            type = "public",
            q = searchingString,
            time = time,
            calories = calories,
            mealType = mealType,
            cuisineType = cuisineType
        )).thenReturn(mockResponse)

        val response = recipeRepository.getList(searchingString, time, calories, mealType, cuisineType)

        assert(response.isSuccessful)
        assert(response.body() != null)
        assert(response.body()!!.hits.isEmpty())
    }

    @Test
    fun `getList should handle error response`() = runBlocking {
        val mockResponse = Response.error<Hit>(404, ResponseBody.create(null, "Not Found"))
        val searchingString = "salmon"
        val time = "1+"
        val calories = "1+"
        val mealType = listOf("Dinner")
        val cuisineType = listOf("American")

        `when`(api.getAllRecipes(
            type = "public",
            q = searchingString,
            time = time,
            calories = calories,
            mealType = mealType,
            cuisineType = cuisineType
        )).thenReturn(mockResponse)

        val response = recipeRepository.getList(searchingString, time, calories, mealType, cuisineType)

        assert(!response.isSuccessful)
    }
}
