package com.example.jobplanet.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jobplanet.R
import com.example.jobplanet.adapter.SearchTabPagerAdapter
import com.example.jobplanet.databinding.FragmentSearchTabBinding
import com.example.jobplanet.viewmodel.JobPlanetVM
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchTabFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchTabBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[JobPlanetVM::class.java]

        binding.viewModel = viewModel

        viewModel.getRecruitItems()
        viewModel.getCellItems()

        // Search View
        val searchTerm = binding.searchTerm.query
        binding.searchTerm.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.onQueryTextSubmit(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.onQueryTextChange(it)
                }
                return false
            }
        })

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
}