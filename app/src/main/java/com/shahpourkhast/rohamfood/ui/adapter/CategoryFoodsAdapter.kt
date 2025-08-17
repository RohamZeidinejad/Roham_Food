package com.shahpourkhast.rohamfood.ui.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.shahpourkhast.rohamfood.R
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.databinding.CategoryFoodsItemBinding

class CategoryFoodsAdapter : ListAdapter<HomeFoodsData.Meal, CategoryFoodsAdapter.CategoryFoodsViewHolder>(CategoryFoodsDiffCallback()) {

    var onItemClick : ((HomeFoodsData.Meal) -> Unit)? = null

    inner class CategoryFoodsViewHolder(private val binding: CategoryFoodsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("PrivateResource")
        fun onBindCodes(categories: HomeFoodsData.Meal) {

            binding.image.visibility = View.VISIBLE
            binding.iconImage.visibility = View.GONE

            Glide.with(binding.root.context)
                .load(categories.strMealThumb)
                .error(R.drawable.ic_img_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>, isFirstResource: Boolean): Boolean {

                        binding.iconImage.visibility = View.VISIBLE
                        binding.image.visibility = View.GONE
                        return false

                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>?, dataSource: DataSource, isFirstResource: Boolean): Boolean {

                        binding.image.visibility = View.VISIBLE
                        binding.iconImage.visibility = View.GONE
                        return false

                    }
                })
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
