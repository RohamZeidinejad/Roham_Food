package com.shahpourkhast.rohamfood.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shahpourkhast.rohamfood.R
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase
import com.shahpourkhast.rohamfood.databinding.FragmentFavoritesBinding
import com.shahpourkhast.rohamfood.ui.adapter.FavoritesAdapter
import com.shahpourkhast.rohamfood.ui.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoritesAdapter: FavoritesAdapter
    private val args: CategoryFoodsFragmentArgs by navArgs()
    private val viewModel: HomeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavoritesBinding.bind(view)

    //---------------------------------------------------------

        setupRecyclerView()

        observeFoodsDb()

    }

    //---------------------------------------------------------

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null

    }

    //---------------------------------------------------------

    private fun setupRecyclerView() {

        binding.recyclerView.apply {

            favoritesAdapter = FavoritesAdapter()

            layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            adapter = favoritesAdapter

        }

        //-----------------------------------------------------------------------------

        favoritesAdapter.onItemClick = { food ->

            val action = FavoritesFragmentDirections.actionFavoritesFragmentToMealDetailFragment(food.idMeal)
            findNavController().navigate(action)

        }


    }

    //---------------------------------------------------------

    private fun observeFoodsDb() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.getFavoriteFoodsDb().collect { food ->
                    favoritesAdapter.submitList(food)

                }

            }

        }

    }

}