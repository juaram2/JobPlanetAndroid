package com.example.jobplanet.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.jobplanet.databinding.FragmentRecruitDetailBinding
import com.example.jobplanet.viewmodel.RecruitDetailVM

private const val ID = "id"

class RecruitDetailFragment : Fragment() {
    private var id: Int? = null
    private lateinit var viewModel: RecruitDetailVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecruitDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[RecruitDetailVM::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        id?.let {
            viewModel.getRecruitItems(it)
        }

        viewModel.recruit.observe(viewLifecycleOwner) {
            // TODO : set placeholder image
            Glide.with(this).load(it.imageUrl).centerCrop().into(binding.thumbnail)
            Glide.with(this).load(it.company?.logoPath).centerCrop().into(binding.companyLogo)
        }

        return binding.root
    }
}