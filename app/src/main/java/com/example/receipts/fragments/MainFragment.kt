package com.example.receipts.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receipts.R
import com.example.receipts.adapter.RecipeListAdapter
import com.example.receipts.databinding.FragmentMainBinding
import com.example.receipts.model.Recipe
import com.example.receipts.service.RecipeRepository
import com.example.receipts.viewmodels.RecipeViewModel
import com.example.receipts.viewmodels.RecipeViewModelFactory


class MainFragment : Fragment() {
    lateinit var viewModel: RecipeViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var recipeListAdapter: RecipeListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.GONE
        viewModel =
            ViewModelProvider(this, RecipeViewModelFactory(RecipeRepository())).get(
                RecipeViewModel::class.java
            )
        recipeListAdapter = RecipeListAdapter(object : RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                val fragment = RecipeFragment()
                val bundle = Bundle()
                bundle.putString("source", recipe.source)
                bundle.putString("time", recipe.totalTime)
                bundle.putString("uri", recipe.uri)
                bundle.putString("name", recipe.label)
                bundle.putString("image", recipe.image)
                bundle.putDouble("calories", recipe.calories)
                bundle.putParcelableArrayList("ingredients", recipe.ingredients)
                fragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
        binding.apply {
            recipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            recipeRecyclerView.adapter = recipeListAdapter
            favoriteBtn.setOnClickListener {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, FavoritesFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }
            resetFilterBtn.setOnClickListener {
                viewModel.time = "1+"
                viewModel.cuisineType = listOf()
                viewModel.calories = "1+"
                viewModel.mealType = listOf()
                viewModel.getAllRecipes()
            }
            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    viewModel.search = s.toString()
                    viewModel.getAllRecipes()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            filterIcon.setOnClickListener { showFilterDialog() }
        }
        viewModel.recipesList.observe(viewLifecycleOwner) {
            recipeListAdapter.setRecipes(it)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
        }
        viewModel.getAllRecipes()
        //binding.progressBar.visibility=View.VISIBLE

    }

    @SuppressLint("MissingInflatedId")
    private fun showFilterDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val filterView = inflater.inflate(R.layout.filter_dialog, null)
        val dialog = builder.setView(filterView)
            .setPositiveButton("Filter") { _, _ ->
                val calorieMinEditText = filterView.findViewById<EditText>(R.id.calorieMinEditText)
                val calorieMaxEditText = filterView.findViewById<EditText>(R.id.calorieMaxEditText)
                val minCalories = calorieMinEditText.text.toString().toIntOrNull()
                val maxCalories = calorieMaxEditText.text.toString().toIntOrNull()

                val timeMinEditText = filterView.findViewById<EditText>(R.id.timeMinEditText)
                val timeMaxEditText = filterView.findViewById<EditText>(R.id.timeMaxEditText)
                val minTime = timeMinEditText.text.toString().toIntOrNull()
                val maxTime = timeMaxEditText.text.toString().toIntOrNull()

                val mealTypeLinearLayout =
                    filterView.findViewById<LinearLayout>(R.id.meal_type_linear_layout)
                val selectedMealTypes = mutableListOf<String>()

                for (i in 0 until mealTypeLinearLayout.childCount) {
                    val childView = mealTypeLinearLayout.getChildAt(i)
                    if (childView is CheckBox) {
                        if (childView.isChecked) {
                            selectedMealTypes.add(childView.text.toString())
                        }
                    }
                }
                viewModel.mealType = selectedMealTypes

                val cuisineLinearLayout =
                    filterView.findViewById<LinearLayout>(R.id.cuisine_linear_layout)
                val selectedCuisineTypes = mutableListOf<String>()

                for (i in 0 until cuisineLinearLayout.childCount) {
                    val childView = cuisineLinearLayout.getChildAt(i)
                    if (childView is CheckBox) {
                        if (childView.isChecked) {
                            selectedCuisineTypes.add(childView.text.toString())
                        }
                    }
                }
                viewModel.cuisineType = selectedCuisineTypes

                setCalories(minCalories, maxCalories)
                setTime(minTime, maxTime)
                viewModel.getAllRecipes()
            }
            .create()

        dialog.show()

        filterView.findViewById<ImageView>(R.id.close_btn).setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun setCalories(minCalories: Int?, maxCalories: Int?) {
        if (minCalories == null && maxCalories == null) {
            return
        } else if (minCalories == null) {
            viewModel.calories = "$maxCalories"
        } else if (maxCalories == null) {
            viewModel.calories = "$minCalories+"
        } else if (minCalories < maxCalories) {
            viewModel.calories = "$minCalories-$maxCalories"
        } else {
            Toast.makeText(requireContext(), "Incorrect range for calories", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setTime(minTime: Int?, maxTime: Int?) {
        if (minTime == null && maxTime == null) {
            return
        } else if (minTime == null) {
            viewModel.time = "$maxTime"
        } else if (maxTime == null) {
            viewModel.time = "$minTime+"
        } else if (minTime < maxTime) {
            viewModel.time = "$minTime-$maxTime"
        } else {
            Toast.makeText(requireContext(), "Incorrect range for time", Toast.LENGTH_SHORT)
                .show()
        }
    }

}