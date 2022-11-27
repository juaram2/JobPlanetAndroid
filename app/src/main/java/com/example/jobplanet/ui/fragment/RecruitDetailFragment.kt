package com.example.jobplanet.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.jobplanet.R
import com.example.jobplanet.databinding.FragmentRecruitDetailBinding
import com.example.jobplanet.ui.activity.RecruitDetailActivity
import com.example.jobplanet.utils.Utils
import com.example.jobplanet.viewmodel.RecruitDetailVM
import com.google.android.material.chip.Chip

class RecruitDetailFragment : Fragment() {
    private val viewModel by activityViewModels<RecruitDetailVM>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecruitDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it == true) {
                (context as RecruitDetailActivity).showProgressView()
            } else {
                (context as RecruitDetailActivity).hideProgressView()
            }
        }

        viewModel.recruit.observe(viewLifecycleOwner) {
            /**
             * Thumbnail
             */
            Utils.imageView(this, it.imageUrl, binding.thumbnail)

            /**
             * Logo
             */
            Utils.imageView(this, it.company?.logoPath, binding.logo)

            /**
             * Highest Rating
             */
            binding.rateAvg.text = Utils.highestNumber(it.company?.ratings)

            /**
             * Appeals
             */
            val appealGroup = binding.appealGroup
            if (!it.appeal.isNullOrEmpty()) {
                val chipGroupInflater = LayoutInflater.from(appealGroup.context)

                val appeals = Utils.stringToList(it.appeal)
                val children = appeals.map {
                    val chip = chipGroupInflater.inflate(R.layout.item_chip_appeal, appealGroup, false) as Chip
                    chip.text = it.trim()
                    chip
                }

                appealGroup.removeAllViews()

                for(chip: Chip in children) {
                    appealGroup.addView(chip)
                }
            }

            /**
             * Ratings
             */
            if (it.company != null) {
                it.company.ratings?.let { ratings ->
                    val recyclerRating = binding.layoutRating
                    val layoutInflater = LayoutInflater.from(recyclerRating.context)

                    val children = ratings.map { child ->
                        val rating = layoutInflater.inflate(
                            R.layout.item_recycler_rating, recyclerRating, false) as ConstraintLayout
                        val type: TextView = rating.findViewById(R.id.type)
                        val rate:TextView = rating.findViewById(R.id.rate)

                        type.text = child.type
                        rate.text = child.rating.toString()

                        rating
                    }
                    recyclerRating.removeAllViews()

                    for (item: ConstraintLayout in children) {
                        recyclerRating.addView(item)
                    }
                }
            }
        }

        return binding.root
    }
}