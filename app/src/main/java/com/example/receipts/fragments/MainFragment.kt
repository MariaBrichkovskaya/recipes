package com.example.receipts.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receipts.R
import com.example.receipts.adapter.RecipeListAdapter
import com.example.receipts.databinding.FragmentMainBinding
import com.example.receipts.model.Recipe
import com.example.receipts.service.RecipeRepository
import com.example.receipts.service.RetrofitService
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
        val retrofitService = RetrofitService.getInstance()
        binding.progressBar.visibility = View.GONE
        viewModel =
            ViewModelProvider(this, RecipeViewModelFactory(RecipeRepository(retrofitService))).get(
                RecipeViewModel::class.java
            )
        recipeListAdapter = RecipeListAdapter(object : RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                val fragment = RecipeFragment()
                val bundle = Bundle()
                bundle.putString("name", recipe.label)
                bundle.putString("image", recipe.image)
                bundle.putInt("calories", recipe.calories.toInt())
                bundle.putParcelableArrayList("ingredients", recipe.ingredients)
                fragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
        binding.apply {
            receiptRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            receiptRecyclerView.adapter = recipeListAdapter
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
        }
        viewModel.recipesList.observe(viewLifecycleOwner) {
            recipeListAdapter.setRecipes(it)
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
        }
        viewModel.getAllRecipes()
        //binding.progressBar.visibility=View.VISIBLE

    }
}