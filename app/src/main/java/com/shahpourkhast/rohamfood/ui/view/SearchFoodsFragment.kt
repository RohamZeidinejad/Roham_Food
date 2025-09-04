package com.shahpourkhast.rohamfood.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.shahpourkhast.rohamfood.R
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase
import com.shahpourkhast.rohamfood.databinding.FragmentSearchFoodsBinding
import com.shahpourkhast.rohamfood.ui.adapter.SearchFoodsAdapter
import com.shahpourkhast.rohamfood.ui.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFoodsFragment : Fragment(R.layout.fragment_search_foods) {
    private var _binding: FragmentSearchFoodsBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchFoodsAdapter: SearchFoodsAdapter
    private val viewModel: HomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSearchFoodsBinding.bind(view)

        //---------------------------------------------------------

        searchFoodsRecyclerView()

        observeSearchFoods()

        searchFood()

    }

    //---------------------------------------------------------

    private fun searchFood(){

        binding.searchFood.addTextChangedListener { text ->

            val searchQuery = text.toString()
            viewModel.searchFoods(searchQuery)

        }

    }

    private fun searchFoodsRecyclerView() {

        searchFoodsAdapter = SearchFoodsAdapter()

        binding.recyclerView.apply {

            adapter = searchFoodsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2 ,  RecyclerView.VERTICAL, false)

            //-------------------------------------------------------

            searchFoodsAdapter.onItemClick = { food ->

                val action = SearchFoodsFragmentDirections.actionSearchFoodsFragmentToMealDetailFragment(food.idMeal)
                findNavController().navigate(action)

            }

        }

    }

    //---------------------------------------------------------

    private fun observeSearchFoods(){

        lifecycleScope.launch {

            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.searchFoods.collect { food ->
                    searchFoodsAdapter.submitList(food?.meals)

                }

            }

        }

    }

    //---------------------------------------------------------

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null

    }

}