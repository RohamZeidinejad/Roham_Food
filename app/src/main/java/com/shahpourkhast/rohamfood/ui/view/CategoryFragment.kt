package com.shahpourkhast.rohamfood.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shahpourkhast.rohamfood.R
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase
import com.shahpourkhast.rohamfood.databinding.FragmentCategoryBinding
import com.shahpourkhast.rohamfood.ui.adapter.CategoriesAdapter
import com.shahpourkhast.rohamfood.ui.viewModel.HomeViewModel
import com.shahpourkhast.rohamfood.ui.viewModel.HomeViewModelFactory

class CategoryFragment : Fragment(R.layout.fragment_category) {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels {
        val database = FoodsDatabase.getDatabase(requireContext())
        HomeViewModelFactory(database)
    }
    private lateinit var categoriesAdapter: CategoriesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCategoryBinding.bind(view)

        //---------------------------------------------------------


        categoriesRecyclerView()

        observeCategories()

    }

    //---------------------------------------------------------

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null

    }


    private fun categoriesRecyclerView() {

        categoriesAdapter = CategoriesAdapter()

        binding.recyclerView.apply {

            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(requireContext() , 3 , RecyclerView.VERTICAL , false)

        }

        //-------------------------------------------------------

        categoriesAdapter.onItemClick = { category ->

            val action = CategoryFragmentDirections.actionCategoryFragmentToCategoryFoodsFragment(category.strCategory)
            findNavController().navigate(action)
        }

    }

    //---------------------------------------------------------

    private fun observeCategories(){

        viewModel.categories.observe(viewLifecycleOwner) { categories ->

            categoriesAdapter.submitList(categories.categories)

        }

    }




}