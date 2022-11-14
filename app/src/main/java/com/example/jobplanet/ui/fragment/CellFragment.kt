package com.example.jobplanet.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobplanet.MainActivity
import com.example.jobplanet.adapter.CellListAdapter
import com.example.jobplanet.adapter.CellListChildAdapterListener
import com.example.jobplanet.databinding.FragmentCellBinding
import com.example.jobplanet.viewmodel.CellVM

private const val SEARCH_TERM = "search_term"

class CellFragment : Fragment() {
    private var searchTerm: String? = null
    private lateinit var viewModel : CellVM

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
        val binding = FragmentCellBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[CellVM::class.java]
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
            viewModel.getCellItems(it)
        } ?: run {
            viewModel.getCellItems()
        }

        val navController = this.findNavController()

        val adapterListener = CellListChildAdapterListener(click = {
            navController.navigate(SearchFragmentDirections.actionToRecruitDetailFragment(it.id))
        })
        val adapter = CellListAdapter(adapterListener)

        viewModel.cells.observe(viewLifecycleOwner) { data ->
            adapter.setData(data)
            adapter.notifyDataSetChanged()
        }

        val layoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.VERTICAL, false
        )

        binding.recyclerViewCell.apply {
            setHasFixedSize(true)
            setLayoutManager(layoutManager)
            setAdapter(adapter)
        }

        with(binding.recyclerViewCell) {
            clipToPadding = false
            clipChildren = false
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(searchTerm: String?) =
            CellFragment().apply {
                arguments = Bundle().apply {
                    putString(SEARCH_TERM, searchTerm)
                }
            }
    }
}