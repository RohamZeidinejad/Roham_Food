package com.shahpourkhast.rohamfood.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shahpourkhast.rohamfood.R
import com.shahpourkhast.rohamfood.databinding.FragmentCategoryFoodsBinding
import com.shahpourkhast.rohamfood.ui.adapter.CategoryFoodsAdapter
import com.shahpourkhast.rohamfood.ui.viewModel.CategoryFoodsViewModel

class CategoryFoodsFragment : Fragment(R.layout.fragment_category_foods) {
    private var _binding: FragmentCategoryFoodsBinding? = null
    private val binding get() = _binding!!
    private val args: CategoryFoodsFragmentArgs by navArgs()
    private val viewModel: CategoryFoodsViewModel by viewModels()
    private lateinit var categoryFoodsAdapter: CategoryFoodsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCategoryFoodsBinding.bind(view)

        //---------------------------------------------------------

        viewModel.getCategoryFoods(args.categoryName)

        //---------------------------------------------------------

        setupRecyclerView()

        observeCategoryFoods()

    }

    //---------------------------------------------------------

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null

    }

    //---------------------------------------------------------

    private fun setupRecyclerView() {

        binding.recyclerView.apply {

            categoryFoodsAdapter = CategoryFoodsAdapter()

            layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            adapter = categoryFoodsAdapter

        }

        //-----------------------------------------------------------------------------

        categoryFoodsAdapter.onItemClick = { food ->

            val action = CategoryFoodsFragmentDirections.actionCategoryFoodsFragmentToMealDetailFragment(food.idMeal)
            findNavController().navigate(action)

        }


    }

    //---------------------------------------------------------


    private fun observeCategoryFoods() {

        viewModel.categoryFoods.observe(viewLifecycleOwner) { foodsList ->

            categoryFoodsAdapter.submitList(foodsList.meals)

        }
    }




}