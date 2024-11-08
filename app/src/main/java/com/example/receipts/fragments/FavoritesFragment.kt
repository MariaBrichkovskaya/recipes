package com.example.receipts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receipts.R
import com.example.receipts.adapter.FavoritesAdapter
import com.example.receipts.databinding.FragmentFavoritesBinding
import com.example.receipts.db.RecipeDatabase
import com.example.receipts.model.RecipeEntity
import com.example.receipts.viewmodels.DBViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var recipeListAdapter: FavoritesAdapter

    private val viewModel: DBViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       recipeListAdapter =
            FavoritesAdapter(
                object : FavoritesAdapter.OnItemClickListener {
                    override fun onItemClick(recipe: RecipeEntity) {
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
            favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            favoritesRecyclerView.adapter = recipeListAdapter
            backBtn.setOnClickListener {
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, MainFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        viewModel.recipesList.observe(viewLifecycleOwner) {
            recipeListAdapter.setRecipes(it)
        }
        viewModel.getAllRecipes()


    }

}