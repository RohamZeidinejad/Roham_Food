package com.shahpourkhast.rohamfood.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.databinding.CategoryFoodsItemBinding

class CategoryFoodsAdapter : ListAdapter<HomeFoodsData.Meal, CategoryFoodsAdapter.CategoryFoodsViewHolder>(CategoryFoodsDiffCallback()) {

    var onItemClick : ((HomeFoodsData.Meal) -> Unit)? = null

    inner class CategoryFoodsViewHolder(private val binding: CategoryFoodsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("PrivateResource")
        fun onBindCodes(categories: HomeFoodsData.Meal) {

            Glide.with(binding.root.context)
                .load(categories.strMealThumb)
                .error(com.google.android.material.R.drawable.ic_clear_black_24)
                .into(binding.image)

            binding.foodName.text = categories.strMeal

            itemView.setOnClickListener {

                onItemClick?.invoke(categories)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryFoodsViewHolder {

       val binding = CategoryFoodsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoryFoodsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CategoryFoodsViewHolder, position: Int) {

        holder.onBindCodes(getItem(position))

    }

}


class CategoryFoodsDiffCallback : DiffUtil.ItemCallback<HomeFoodsData.Meal>() {

    override fun areItemsTheSame(oldItem: HomeFoodsData.Meal, newItem: HomeFoodsData.Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: HomeFoodsData.Meal, newItem: HomeFoodsData.Meal): Boolean {
        return oldItem == newItem
    }

}
