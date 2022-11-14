package com.example.jobplanet.adapter

import com.example.jobplanet.model.RecruitItemModel

class CellListChildAdapterListener(val click: (item: RecruitItemModel) -> Unit) {
    fun onClick(item: RecruitItemModel) = click(item)
}