package com.example.jobplanet.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jobplanet.MainActivity
import com.example.jobplanet.adapter.RecruitListAdapter
import com.example.jobplanet.adapter.RecruitListAdapterListener
import com.example.jobplanet.databinding.FragmentRecruitBinding
import com.example.jobplanet.ui.activity.RecruitDetailActivity
import com.example.jobplanet.viewmodel.RecruitVM

private const val SEARCH_TERM = "search_term"

class RecruitFragment : Fragment() {
    private var searchTerm: String? = null
    private lateinit var viewModel : RecruitVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchTerm = it.getString(SEARCH_TERM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecruitBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[RecruitVM::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it == true) {
                (context as MainActivity).showProgressView()
            } else {
                (context as MainActivity).hideProgressView()
            }
        }

        searchTerm?.let {
            viewModel.getRecruitItems(it)
        } ?: run {
            viewModel.getRecruitItems()
        }

        val adapterListener = RecruitListAdapterListener(click = {
            startActivity(RecruitDetailActivity.recruitDetailIntent(context!!, it.id))
        })
        val adapter = RecruitListAdapter(adapterListener)

        viewModel.recruits.observe(viewLifecycleOwner) { data ->
            adapter.setData(data)
            adapter.notifyDataSetChanged()
        }

        val layoutManager = GridLayoutManager(
            activity, 2, GridLayoutManager.VERTICAL, false
        )

        binding.recyclerViewRecruit.apply {
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            setAdapter(adapter)
        }

        with(binding.recyclerViewRecruit) {
            clipToPadding = false
            clipChildren = false
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(searchTerm: String?) =
            RecruitFragment().apply {
                arguments = Bundle().apply {
                    putString(SEARCH_TERM, searchTerm)
                }
            }
    }
}