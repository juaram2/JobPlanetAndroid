package com.example.jobplanet.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jobplanet.R

private const val SEARCH_TERM = "search_term"

class RecruitFragment : Fragment() {
    private var searchTerm: CharSequence? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchTerm = it.getCharSequence(SEARCH_TERM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recruit, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param searchTerm Parameter 1.
         * @return A new instance of fragment RecruitFragment.
         */
        @JvmStatic
        fun newInstance(searchTerm: CharSequence?) =
            RecruitFragment().apply {
                arguments = Bundle().apply {
                    putCharSequence(SEARCH_TERM, searchTerm)
                }
            }
    }
}