package com.example.jobplanet.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jobplanet.R
import com.example.jobplanet.adapter.SearchTabPagerAdapter
import com.example.jobplanet.databinding.FragmentSearchResultBinding
import com.example.jobplanet.viewmodel.BaseVM
import com.example.jobplanet.viewmodel.CellVM
import com.example.jobplanet.viewmodel.RecruitVM
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val SEARCH_TERM = "search_term"

class SearchResultFragment : Fragment() {
    private var searchTerm: String? = null

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
        val binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        val recruitVM = ViewModelProvider(this)[RecruitVM::class.java]
        val cellVM = ViewModelProvider(this)[CellVM::class.java]

        binding.recruitVM = recruitVM
        binding.cellVM = cellVM
        binding.lifecycleOwner = this

        // Tab Layout
        activity?.let { activity ->
            binding.tabLayout.let { tab ->
                tab.tabMode = TabLayout.MODE_FIXED
                tab.tabGravity = TabLayout.GRAVITY_FILL

                binding.viewPager.let { pager ->
                    pager.adapter = SearchTabPagerAdapter(activity, searchTerm)
                    pager.isUserInputEnabled = false

                    TabLayoutMediator(tab, pager) { tab, position ->
                        var title = ""
                        when (position) {
                            0 -> { title = getString(R.string.tab_recruit) }
                            1 -> { title = getString(R.string.tab_cell) }
                        }
                        tab.text = title
                    }.attach()
                }
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(searchTerm: String) =
            SearchResultFragment().apply {
                arguments = Bundle().apply {
                    putString(SEARCH_TERM, searchTerm)
                }
            }
    }
}