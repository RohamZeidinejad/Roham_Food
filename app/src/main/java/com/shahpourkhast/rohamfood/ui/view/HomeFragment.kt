package com.shahpourkhast.rohamfood.ui.view

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.request.target.Target
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.shahpourkhast.rohamfood.R
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase
import com.shahpourkhast.rohamfood.databinding.FragmentHomeBinding
import com.shahpourkhast.rohamfood.ui.adapter.CategoriesAdapter
import com.shahpourkhast.rohamfood.ui.adapter.SeaFoodsAdapter
import com.shahpourkhast.rohamfood.ui.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var seaFoodsAdapter: SeaFoodsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        //---------------------------------------------------------

        observeLetsMakeThis()

        seaFoodsRecyclerView()

        observeSeaFoods()

        categoriesRecyclerView()

        observeCategories()

        goToSearchFragment()

    }

    //---------------------------------------------------------

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //---------------------------------------------------------

    @SuppressLint("PrivateResource")
    private fun observeLetsMakeThis() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.letsMakeThis.collect { food ->

                    food?.let {
                        if (it.meals.isNotEmpty()) {
                            val randomFood = it.meals[0]
                            val imageUrl = randomFood.strMealThumb

                            Glide.with(requireContext())
                                .load(imageUrl)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
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
                                }).into(binding.image)

                            binding.imageCard.setOnClickListener {
                                val action = HomeFragmentDirections.actionHomeFragmentToMealDetailFragment(randomFood.idMeal)
                                findNavController().navigate(action)

                            }

                        }

                    }

                }

            }

        }

    }

    //---------------------------------------------------------

    private fun seaFoodsRecyclerView() {

        seaFoodsAdapter = SeaFoodsAdapter()

        binding.recyclerView1.apply {

            adapter = seaFoodsAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

            //-------------------------------------------------------

            seaFoodsAdapter.onItemClick = { meal ->

                val action = HomeFragmentDirections.actionHomeFragmentToMealDetailFragment(meal.idMeal)
                findNavController().navigate(action)

            }

        }

    }

    //---------------------------------------------------------

    private fun observeSeaFoods() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.seaFoods.collect { seaFoods ->
                    seaFoods?.let {

                        seaFoodsAdapter.submitList(it.meals)

                    }

                }

            }

        }

    }

    //---------------------------------------------------------

    private fun categoriesRecyclerView() {

        categoriesAdapter = CategoriesAdapter()

        binding.recyclerView2.apply {

            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(requireContext() , 3 , RecyclerView.VERTICAL , false)

        }

        //-------------------------------------------------------

        categoriesAdapter.onItemClick = { category ->

            val action = HomeFragmentDirections.actionHomeFragmentToCategoryFoodsFragment(category.strCategory)
            findNavController().navigate(action)
        }

    }

    //---------------------------------------------------------

    private fun observeCategories(){

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.categories.collect { categories ->
                    categories?.let {

                        categoriesAdapter.submitList(it.categories)

                    }

                }

            }

        }

    }


    private fun goToSearchFragment(){

        binding.icSearch.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_searchFoodsFragment)

        }



    }


}