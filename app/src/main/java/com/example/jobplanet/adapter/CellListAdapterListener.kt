package com.example.jobplanet.adapter

import com.example.jobplanet.model.RecruitItemModel

class CellListAdapterListener(val click: (item: List<RecruitItemModel>) -> Unit) {
    fun onClick(item: List<RecruitItemModel>) = click(item)
}