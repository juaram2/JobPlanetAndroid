package com.example.jobplanet.adapter

import com.example.jobplanet.model.CellItemModel

class CellListAdapterListener(val click: (item: CellItemModel) -> Unit) {
    fun onClick(item: CellItemModel) = click(item)
}