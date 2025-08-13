package com.shahpourkhast.rohamfood.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shahpourkhast.rohamfood.data.dataClass.CategoriesData
import com.shahpourkhast.rohamfood.databinding.CategoriesItemBinding

class CategoriesAdapter : ListAdapter<CategoriesData.Category, CategoriesAdapter.CategoriesViewHolder>(CategoriesDiffCallback()) {

    var onItemClick: ((CategoriesData.Category) -> Unit)? = null

    inner class CategoriesViewHolder(private val binding: CategoriesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBindCodes(categories: CategoriesData.Category) {

            Glide.with(binding.root.context)
                .load(categories.strCategoryThumb)
                .error(com.google.android.material.R.drawable.ic_clear_black_24)
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
