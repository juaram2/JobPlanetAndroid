package com.example.jobplanet.adapter

import com.example.jobplanet.model.RecruitItemModel

class RecruitListAdapterListener(val click: (item: RecruitItemModel) -> Unit) {
    fun onClick(item: RecruitItemModel) = click(item)
}