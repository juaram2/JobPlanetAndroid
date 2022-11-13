package com.example.jobplanet.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jobplanet.R
import com.example.jobplanet.adapter.SearchTabPagerAdapter
import com.example.jobplanet.databinding.FragmentSearchTabBinding
import com.example.jobplanet.viewmodel.RecruitVM
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchTabFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchTabBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this)[RecruitVM::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Search View
        var searchTerm = binding.searchView.query
        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.onQueryTextChange(newText)
                    searchTerm = newText
                }
                return false
            }
        })

        // Tab Layout
        searchTerm.let {
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
        }

        return binding.root
    }

    fun hideKeyboard() {
        activity?.apply {
            if (currentFocus != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view?.windowToken, 0)
            } else {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            }
        }
    }
}