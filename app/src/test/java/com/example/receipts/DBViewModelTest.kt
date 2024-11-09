package com.example.receipts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.receipts.db.RecipeDao
import com.example.receipts.model.RecipeEntity
import com.example.receipts.viewmodels.DBViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import kotlin.coroutines.CoroutineContext


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DBViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: DBViewModel

    @Mock
    private lateinit var recipeDao: RecipeDao

    private val testDispatcher = TestCoroutineDispatcher()
    private val recipe = RecipeEntity("", "Recipe 1","",60.6,"","15", arrayListOf())

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DBViewModel(recipeDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testGetAllRecipes() = runBlockingTest {
        val recipes = listOf(
            RecipeEntity("", "Recipe 1","",60.6,"","15", arrayListOf()),
            RecipeEntity("", "Recipe 2","",60.6,"","16", arrayListOf())
        )
        `when`(recipeDao.getAll()).thenReturn(recipes)

        viewModel.getAllRecipes()

        advanceUntilIdle()

        viewModel.recipesList.observeForever { }
        assertEquals(recipes, viewModel.recipesList.value)
        verify(recipeDao).getAll()
    }

    @Test
    fun testInsertRecipe() = runBlockingTest{
        viewModel.insert(recipe)

        advanceUntilIdle()

        verify(recipeDao).insert(recipe)
    }

    @Test
    fun testDeleteRecipe() = runBlockingTest {
        viewModel.delete(recipe)

        advanceUntilIdle()

        verify(recipeDao).delete(recipe)
    }

    @Test
    fun testGetByLabel() = runBlockingTest {
        val label = "Dessert"
        `when`(recipeDao.getByLabel(label)).thenReturn(MutableLiveData(recipe))

        viewModel.getByLabel(label)

        advanceUntilIdle()

        viewModel.recipeLiveData.observeForever { }
        assertEquals(recipe, viewModel.recipeLiveData.value)
        verify(recipeDao).getByLabel(label)
    }
}
