package com.shahpourkhast.rohamfood.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.databinding.SeaFoodItemBinding

class SeaFoodsAdapter : ListAdapter<HomeFoodsData.Meal, SeaFoodsAdapter.SeaFoodsViewHolder>(SeaFoodsDiffCallback()) {

    var onItemClick: ((HomeFoodsData.Meal) -> Unit)? = null

    inner class SeaFoodsViewHolder(val binding: SeaFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBindCodes(seaFood: HomeFoodsData.Meal) {

            Glide
                .with(binding.root.context)
                .load(seaFood.strMealThumb)
                .error(com.google.android.material.R.drawable.ic_clear_black_24)
                .into(binding.image)

            itemView.setOnClickListener {

                onItemClick?.invoke(seaFood)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeaFoodsViewHolder {

       val binding = SeaFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SeaFoodsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SeaFoodsViewHolder, position: Int) {

        holder.onBindCodes(getItem(position))

    }

}


class SeaFoodsDiffCallback : DiffUtil.ItemCallback<HomeFoodsData.Meal>() {

    override fun areItemsTheSame(oldItem: HomeFoodsData.Meal, newItem: HomeFoodsData.Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: HomeFoodsData.Meal, newItem: HomeFoodsData.Meal): Boolean {
        return oldItem == newItem
    }

}
