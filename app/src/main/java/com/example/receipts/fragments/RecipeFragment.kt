package com.example.receipts.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receipts.R
import com.example.receipts.adapter.IngredientAdapter
import com.example.receipts.databinding.FragmentRecipeBinding
import com.example.receipts.db.RecipeDatabase
import com.example.receipts.model.Ingredient
import com.example.receipts.model.RecipeEntity
import com.example.receipts.viewmodels.DBViewModel
import com.example.receipts.viewmodels.DBViewModelFactory
import com.squareup.picasso.Picasso

class RecipeFragment : Fragment() {
    companion object {
        var name: String = ""
        var calories: Double = 0.0
        var source: String = ""
        var uri: String = ""
        var time: String = ""
        lateinit var ingredients: ArrayList<Ingredient>
    }

    private lateinit var binding: FragmentRecipeBinding
    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var viewModel: DBViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = arguments?.getString("name") ?: ""
        source = arguments?.getString("source") ?: ""
        time = arguments?.getString("time") ?: ""
        uri = arguments?.getString("uri") ?: ""
        val imageUrl = arguments?.getString("image") ?: ""
        calories = arguments?.getDouble("calories") ?: 0.0
        Picasso.get().load(imageUrl).into(binding.dishImage)
        val database = RecipeDatabase.getInstance(requireContext())
        ingredients = arguments?.getParcelableArrayList("ingredients") ?: ArrayList()
        ingredientAdapter =
            IngredientAdapter(ingredients)
        viewModel = ViewModelProvider(this, DBViewModelFactory(database.recipeDao())).get(
            DBViewModel::class.java
        )
        binding.ingredientRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.ingredientRecyclerView.adapter = ingredientAdapter
        binding.apply {
            dishName.text = name
            caloriesTw.text = "$calories kcal"
            viewModel.getByLabel(name)
            var isFavorite = false
            val recipeEntity = RecipeEntity(
                label = name,
                image = imageUrl,
                calories = calories,
                source = source,
                totalTime = time,
                ingredients = ingredients,
                uri = uri
            )
            viewModel.recipeLiveData.observe(requireActivity()) { recipe ->
                if (recipe != null) {
                    isFavorite = true
                    favoriteIcon.setImageResource(R.drawable.star_selector_icon)
                } else {
                    isFavorite = false
                    favoriteIcon.setImageResource(R.drawable.star_icon)
                }
            }
            favoriteIcon.setOnClickListener {
                if (isFavorite) {
                    isFavorite = false
                    favoriteIcon.setImageResource(R.drawable.star_icon)
                    viewModel.delete(recipeEntity)
                } else {
                    isFavorite = true
                    favoriteIcon.setImageResource(R.drawable.star_selector_icon)
                    viewModel.insert(recipeEntity)
                }
            }

            backBtn.setOnClickListener {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, MainFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}
