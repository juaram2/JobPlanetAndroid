package com.example.jobplanet.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.jobplanet.databinding.ItemRecyclerCellHotizontalThemeBinding
import com.example.jobplanet.model.CellItemModel


class CellHorizontalViewHolder(private val binding: ItemRecyclerCellHotizontalThemeBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CellItemModel) {
        binding.item = item
        binding.executePendingBindings()
    }
}
