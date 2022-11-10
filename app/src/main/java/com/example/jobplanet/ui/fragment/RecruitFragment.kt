package com.example.jobplanet.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jobplanet.adapter.RecruitListAdapter
import com.example.jobplanet.adapter.RecruitListAdapterListener
import com.example.jobplanet.databinding.FragmentRecruitBinding
import com.example.jobplanet.viewmodel.JobPlanetVM

private const val SEARCH_TERM = "search_term"

class RecruitFragment : Fragment() {
    private var searchTerm: CharSequence? = null
    private lateinit var viewModel : JobPlanetVM

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
        val binding = FragmentRecruitBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[JobPlanetVM::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getRecruitItems()

        val navController = this.findNavController()

        val adapterListener = RecruitListAdapterListener(click = {
//            navController.navigate()
        })
        val adapter = RecruitListAdapter(adapterListener)

        viewModel.recruits.observe(viewLifecycleOwner) { data ->
            Log.d("debug","!!!!!!!!!!!!")
            adapter.setData(data)
            adapter.notifyDataSetChanged()
            data?.let {
                viewModel.noData(data.isEmpty())
            }
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