package com.example.receipts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receipts.R
import com.example.receipts.adapter.IngredientAdapter
import com.example.receipts.databinding.FragmentRecipeBinding
import com.squareup.picasso.Picasso

class RecipeFragment : Fragment() {
    companion object{
        var name:String=""
        var calories:Int=0
    }
    lateinit var binding: FragmentRecipeBinding
    lateinit var  ingredientAdapter: IngredientAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name= arguments?.getString("name")?:""
        val imageUrl = arguments?.getString("image") ?: ""
        calories=arguments?.getInt("calories")?:0
        Picasso.get().load(imageUrl).into(binding.dishImage)
        ingredientAdapter= IngredientAdapter(arguments?.getParcelableArrayList("ingredients")?:ArrayList())
        binding.ingredientRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.ingredientRecyclerView.adapter = ingredientAdapter
        binding.apply {
            dishName.text= name
            caloriesTw.text= "${calories} kkal"
            backBtn.setOnClickListener{
                val transaction=requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container,MainFragment())
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}