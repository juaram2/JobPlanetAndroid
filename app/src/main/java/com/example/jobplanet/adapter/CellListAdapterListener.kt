package com.example.jobplanet.adapter

import com.example.jobplanet.model.CellItemsModel

class CellListAdapterListener(val click: (item: CellItemsModel) -> Unit) {
    fun onClick(item: CellItemsModel) = click(item)
}