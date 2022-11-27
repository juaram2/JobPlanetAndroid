package com.example.jobplanet.adapter.holder

import androidx.recyclerview.widget.RecyclerView
import com.example.jobplanet.databinding.ItemRecyclerCellCompanyBinding
import com.example.jobplanet.model.CellItemModel


class CompanyViewHolder(private val binding: ItemRecyclerCellCompanyBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CellItemModel) {
        binding.item = item
        binding.executePendingBindings()
    }
}
