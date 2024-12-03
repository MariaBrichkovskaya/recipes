package com.example.receipts.unit

import com.example.receipts.model.Hit
import com.example.receipts.service.Api
import com.example.receipts.service.RecipeRepository
import kotlinx.coroutines.test.*

import org.mockito.Mock
import org.mockito.Mockito.*

import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
    fun` getList should return successful response`() = runBlockingTest {
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
    fun `getList should handle error response`() = runBlockingTest {
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
