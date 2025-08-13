package com.shahpourkhast.rohamfood.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.databinding.SearchFoodItemBinding

class SearchFoodsAdapter : ListAdapter<HomeFoodsData.Meal, SearchFoodsAdapter.FavoritesViewHolder>(SearchFoodsCallback()) {

    var onItemClick : ((HomeFoodsData.Meal) -> Unit)? = null

    inner class FavoritesViewHolder(private val binding: SearchFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("PrivateResource")
        fun onBindCodes(food: HomeFoodsData.Meal) {

            Glide.with(binding.root.context)
                .load(food.strMealThumb)
                .error(com.google.android.material.R.drawable.ic_clear_black_24)
                .into(binding.image)

            binding.foodName.text = food.strMeal


           itemView.setOnClickListener {

                onItemClick?.invoke(food)

            }



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {

       val binding = SearchFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavoritesViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {

        holder.onBindCodes(getItem(position))

    }

}


class SearchFoodsCallback : DiffUtil.ItemCallback<HomeFoodsData.Meal>() {

    override fun areItemsTheSame(oldItem: HomeFoodsData.Meal, newItem: HomeFoodsData.Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: HomeFoodsData.Meal, newItem: HomeFoodsData.Meal): Boolean {
        return oldItem == newItem
    }

}
