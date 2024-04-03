package com.example.receipts.adapter

import com.example.receipts.model.Recipe

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.receipts.databinding.RecipeItemViewBinding

class RecipeListAdapter(private var recipeModelList: List<Recipe>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<RecipeListAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(recipe: Recipe)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setRecipes(recipes :List<Recipe>){
        this.recipeModelList = recipes.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: RecipeItemViewBinding, private val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(model: Recipe) {
            binding.apply {
                dishName.text = model.name
                dishTime.text = "${model.category} min"
                Glide.with(itemView)
                    .load(model.imageUrl)
                    .centerCrop()
                    .into(dishImage)
                root.setOnClickListener {
                    listener.onItemClick(model)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecipeItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return recipeModelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(recipeModelList[position])
    }

}