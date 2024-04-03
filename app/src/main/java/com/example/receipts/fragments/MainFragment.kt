package com.example.receipts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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
    lateinit var  viewModel: RecipeViewModel
    lateinit var binding: FragmentMainBinding
    lateinit var  adapter: RecipeListAdapter
    lateinit var recipeList : MutableList<Recipe>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val retrofitService = RetrofitService.getInstance()
        viewModel = ViewModelProvider(this, RecipeViewModelFactory(RecipeRepository(retrofitService))).get(RecipeViewModel::class.java)
        adapter= RecipeListAdapter(recipeList, object : RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                val fragment = RecipeFragment()
                val bundle = Bundle()
                fragment.arguments = bundle
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
        binding.receiptRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.receiptRecyclerView.adapter = adapter
        viewModel.recipesList.observe(viewLifecycleOwner, Observer {
            adapter.setRecipes(it)
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
        })
        viewModel.getAllRecipes()

    }
}