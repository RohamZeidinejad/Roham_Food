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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.shahpourkhast.rohamfood.R
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.databinding.FavoritesItemBinding

class FavoritesAdapter : ListAdapter<HomeFoodsData.Meal, FavoritesAdapter.FavoritesViewHolder>(FavoritesDiffCallback()) {

    var onItemClick : ((HomeFoodsData.Meal) -> Unit)? = null

    inner class FavoritesViewHolder(private val binding: FavoritesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("PrivateResource")
        fun onBindCodes(food: HomeFoodsData.Meal) {

            Glide.with(binding.root.context)
                .load(food.strMealThumb)
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

            binding.foodName.text = food.strMeal


           itemView.setOnClickListener {

                onItemClick?.invoke(food)

            }



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {

       val binding = FavoritesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavoritesViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {

        holder.onBindCodes(getItem(position))

    }

}


class FavoritesDiffCallback : DiffUtil.ItemCallback<HomeFoodsData.Meal>() {

    override fun areItemsTheSame(oldItem: HomeFoodsData.Meal, newItem: HomeFoodsData.Meal): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: HomeFoodsData.Meal, newItem: HomeFoodsData.Meal): Boolean {
        return oldItem == newItem
    }

}
