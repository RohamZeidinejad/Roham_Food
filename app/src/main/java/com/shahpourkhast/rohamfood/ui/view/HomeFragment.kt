package com.shahpourkhast.rohamfood.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shahpourkhast.rohamfood.R
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase
import com.shahpourkhast.rohamfood.databinding.FragmentHomeBinding
import com.shahpourkhast.rohamfood.ui.adapter.CategoriesAdapter
import com.shahpourkhast.rohamfood.ui.adapter.SeaFoodsAdapter
import com.shahpourkhast.rohamfood.ui.viewModel.HomeViewModel
import com.shahpourkhast.rohamfood.ui.viewModel.HomeViewModelFactory

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels {
        val database = FoodsDatabase.getDatabase(requireContext())
        HomeViewModelFactory(database)
    }
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

        viewModel.letsMakeThis.observe(viewLifecycleOwner) { food ->

            if (food.meals.isNotEmpty()) {
                val randomFood = food.meals[0]
                val imageUrl = randomFood.strMealThumb

                Glide
                    .with(requireContext())
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(com.google.android.material.R.drawable.ic_clear_black_24)
                    .into(binding.image)

                //-----------------------------------------------------------------------------

                binding.imageCard.setOnClickListener {

                    val action = HomeFragmentDirections.actionHomeFragmentToMealDetailFragment(randomFood.idMeal)
                    findNavController().navigate(action)

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

        viewModel.seaFoods.observe(viewLifecycleOwner) { seaFoods ->

            seaFoodsAdapter.submitList(seaFoods.meals)

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

        viewModel.categories.observe(viewLifecycleOwner) { categories ->

            categoriesAdapter.submitList(categories.categories)

        }

    }


    private fun goToSearchFragment(){

        binding.icSearch.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_searchFoodsFragment)

        }



    }


}