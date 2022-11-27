package com.example.jobplanet.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.jobplanet.databinding.ItemRecyclerCellReviewBinding
import com.example.jobplanet.model.CellItemModel


class ReviewViewHolder(private val binding: ItemRecyclerCellReviewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CellItemModel) {
        binding.item = item
        binding.executePendingBindings()
    }
}
