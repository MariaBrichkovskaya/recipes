package com.example.receipts.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.receipts.databinding.RecipeItemViewBinding
import com.example.receipts.model.RecipeEntity

class FavoritesAdapter(listener: OnItemClickListener) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {
    private var listenerContact: OnItemClickListener = listener
    private var recipeModelList = mutableListOf<RecipeEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setRecipes(recipes: List<RecipeEntity>) {
        this.recipeModelList = recipes.toMutableList()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(recipeEntity: RecipeEntity)
    }

    class FavoritesViewHolder(
        private val binding: RecipeItemViewBinding,
        private val listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: RecipeEntity) {
            binding.apply {
                dishName.text = model.label
                caloriesTw.text = "${model.calories.toInt()} kcal"
                dishTime.text = "${model.totalTime} min"
                Glide.with(itemView)
                    .load(model.image)
                    .centerCrop()
                    .into(dishImage)
                root.setOnClickListener {
                    listener.onItemClick(model)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val binding =
            RecipeItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesViewHolder(binding, listenerContact)
    }

    override fun getItemCount(): Int {
        return recipeModelList.size
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val recipe: RecipeEntity = recipeModelList[position]
        holder.bind(recipe)
    }
}