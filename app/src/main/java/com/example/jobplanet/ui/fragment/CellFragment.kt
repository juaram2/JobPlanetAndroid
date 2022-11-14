package com.example.jobplanet.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jobplanet.adapter.CellListAdapter
import com.example.jobplanet.adapter.CellListChildAdapter
import com.example.jobplanet.adapter.CellListChildAdapterListener
import com.example.jobplanet.databinding.FragmentCellBinding
import com.example.jobplanet.viewmodel.CellVM

private const val SEARCH_TERM = "search_term"

class CellFragment : Fragment() {
    private var searchTerm: CharSequence? = null
    private lateinit var viewModel : CellVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchTerm = it.getCharSequence(SEARCH_TERM)
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

        searchTerm?.let {
            viewModel.getCellItems(searchTerm.toString())
        } ?: run {
            viewModel.getCellItems()
        }

        val navController = this.findNavController()

        val cellListChildAdapterListener = CellListChildAdapterListener(click = {
            navController.navigate(SearchTabFragmentDirections.actionToRecruitDetailFragment(it.id))
        })
        val cellListAdapter = CellListAdapter(cellListChildAdapterListener)

        viewModel.cells.observe(viewLifecycleOwner) { data ->
            cellListAdapter.setData(data)
            cellListAdapter.notifyDataSetChanged()
            data?.let {
                viewModel.noData(data.isEmpty())
            }
        }

        val cellsLayoutManager = LinearLayoutManager(
            activity, LinearLayoutManager.VERTICAL, false
        )

        binding.recyclerViewCell.apply {
            setHasFixedSize(true)
            layoutManager = cellsLayoutManager
            adapter = cellListAdapter
        }

        with(binding.recyclerViewCell) {
            clipToPadding = false
            clipChildren = false
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param searchTerm Parameter 1.
         * @return A new instance of fragment CellFragment.
         */
        @JvmStatic
        fun newInstance(searchTerm: CharSequence?) =
            CellFragment().apply {
                arguments = Bundle().apply {
                    putCharSequence(SEARCH_TERM, searchTerm)
                }
            }
    }
}