package com.shahpourkhast.rohamfood.ui.adapter

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
import com.shahpourkhast.rohamfood.data.dataClass.CategoriesData
import com.shahpourkhast.rohamfood.databinding.CategoriesItemBinding

class CategoriesAdapter : ListAdapter<CategoriesData.Category, CategoriesAdapter.CategoriesViewHolder>(CategoriesDiffCallback()) {

    var onItemClick: ((CategoriesData.Category) -> Unit)? = null

    inner class CategoriesViewHolder(private val binding: CategoriesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBindCodes(categories: CategoriesData.Category) {

            Glide.with(binding.root.context)
                .load(categories.strCategoryThumb)
                .error(R.drawable.ic_loading)
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


            binding.category.text = categories.strCategory

            itemView.setOnClickListener {

                onItemClick?.invoke(categories)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {

       val binding = CategoriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoriesViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {

        holder.onBindCodes(getItem(position))

    }

}


class CategoriesDiffCallback : DiffUtil.ItemCallback<CategoriesData.Category>() {

    override fun areItemsTheSame(oldItem: CategoriesData.Category, newItem: CategoriesData.Category): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: CategoriesData.Category, newItem: CategoriesData.Category): Boolean {
        return oldItem == newItem
    }

}
