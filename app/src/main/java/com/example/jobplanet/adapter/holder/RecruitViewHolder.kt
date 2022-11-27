package com.example.jobplanet.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.jobplanet.databinding.FragmentRecruitBinding
import com.example.jobplanet.databinding.ItemRecyclerRecruitBinding
import com.example.jobplanet.model.RecruitItemModel


class RecruitViewHolder(private val binding: ItemRecyclerRecruitBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecruitItemModel) {
        binding.item = item
        binding.executePendingBindings()
    }
}
