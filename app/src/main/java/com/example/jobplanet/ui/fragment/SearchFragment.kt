package com.example.jobplanet.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.jobplanet.R
import com.example.jobplanet.databinding.FragmentSearchTabBinding
import com.example.jobplanet.viewmodel.CellVM
import com.example.jobplanet.viewmodel.RecruitVM

class SearchFragment : Fragment() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var recruitVM: RecruitVM
    private lateinit var cellVM: CellVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSearchTabBinding.inflate(inflater, container, false)
        recruitVM = ViewModelProvider(this)[RecruitVM::class.java]
        cellVM = ViewModelProvider(this)[CellVM::class.java]

        binding.recruitVM = recruitVM
        binding.cellVM = cellVM
        binding.lifecycleOwner = this

        childFragmentManager.beginTransaction().apply {
            this.replace(R.id.search_container, SearchResultFragment())
            this.commit()
        }

        // Search View
        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    childFragmentManager.beginTransaction().apply {
                        this.replace(R.id.search_container, SearchResultFragment.newInstance(query))
                        this.commit()
                    }
                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    childFragmentManager.beginTransaction().apply {
                        this.replace(
                            R.id.search_container,
                            SearchResultFragment.newInstance(newText)
                        )
                        this.commit()
                    }
                }, 500)
                return false
            }
        })

        return binding.root
    }

    /**
     * Hide Keyboard
     */
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