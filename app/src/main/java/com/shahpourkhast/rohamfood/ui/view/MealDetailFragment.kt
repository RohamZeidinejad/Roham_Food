package com.shahpourkhast.rohamfood.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.shahpourkhast.rohamfood.R
import com.shahpourkhast.rohamfood.data.database.FoodsDatabase
import com.shahpourkhast.rohamfood.data.database.HomeFoodsData
import com.shahpourkhast.rohamfood.databinding.CustomToastBinding
import com.shahpourkhast.rohamfood.databinding.FragmentMealDetailBinding
import com.shahpourkhast.rohamfood.ui.viewModel.MealDetailViewModel
import com.shahpourkhast.rohamfood.ui.viewModel.MealDetailViewModelFactory

class MealDetailFragment : Fragment(R.layout.fragment_meal_detail) {
    private var _binding: FragmentMealDetailBinding? = null
    private val binding get() = _binding!!
    private var isMealInFavorites = false
    private val args: MealDetailFragmentArgs by navArgs()
    private var food: HomeFoodsData.Meal? = null
    private val viewModel: MealDetailViewModel by viewModels {
        val database = FoodsDatabase.getDatabase(requireContext())
        MealDetailViewModelFactory(database)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMealDetailBinding.bind(view)

        //---------------------------------------------------------

        binding.collapsingToolBar.title = "Roham Food"

        //---------------------------------------------------------

        val mealId = args.mealId
        viewModel.getMealDetail(mealId)

        //---------------------------------------------------------

        observeMealDetail()

        collapsingToolbarOptions()

        favoriteButton()

        observeFavoriteStatus()

    }

    //---------------------------------------------------------

    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null

    }

    //---------------------------------------------------------

    private fun observeMealDetail() {

        viewModel.mealData.observe(viewLifecycleOwner) { mealData ->

            if (mealData != null && mealData.meals.isNotEmpty()) {

                val meal = mealData.meals[0]

                food = meal

                Glide
                    .with(requireContext())
                    .load(meal.strMealThumb)
                    .error(R.drawable.ic_img_error)
                    .into(binding.image)

                binding.name.text = meal.strMeal
                binding.area.text = meal.strArea
                binding.category.text = meal.strCategory
                binding.instruction.text = meal.strInstructions

            }
        }
    }

    //---------------------------------------------------------

    private fun collapsingToolbarOptions() {

        binding.collapsingToolBar.setExpandedTitleColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.transparent
            )
        )

    }

    //---------------------------------------------------------

    private fun favoriteButton() {

        binding.favoriteButton.setOnClickListener {

            isMealInFavorites = !isMealInFavorites

            if (isMealInFavorites) {

                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_bold)

                viewModel.insertfoodDb(food!!)
                showCustomToast("Food added to favorites")

            } else {

                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_outline)

                viewModel.deletefoodDb(food!!)
                showCustomToast("Food removed from favorites")

            }
        }
    }

    //---------------------------------------------------------

    private fun observeFavoriteStatus() {

        viewModel.observeMealInFavorites(args.mealId).observe(viewLifecycleOwner) { food ->

            if (food != null) {

                isMealInFavorites = true
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_bold)

            } else {

                isMealInFavorites = false
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_outline)

            }
        }
    }

    //---------------------------------------------------------

    private fun showCustomToast(message: String) {
        val inflater = layoutInflater
        val binding = CustomToastBinding.inflate(inflater)

        binding.toastMessage.text = message
        binding.toastIcon.setImageResource(R.drawable.ic_info)

        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = binding.root
        toast.show()
    }

}