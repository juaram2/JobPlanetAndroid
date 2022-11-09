package com.example.jobplanet.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jobplanet.ui.fragment.CellFragment
import com.example.jobplanet.ui.fragment.RecruitFragment

class SearchTabPagerAdapter(activity: FragmentActivity, private val searchTerm: CharSequence?): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                RecruitFragment.newInstance(searchTerm)
            }
            else -> {
                CellFragment.newInstance(searchTerm)
            }
        }
    }
}