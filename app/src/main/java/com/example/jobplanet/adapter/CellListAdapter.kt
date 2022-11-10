package com.example.jobplanet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jobplanet.R
import com.example.jobplanet.model.CellItemModel
import com.example.jobplanet.utils.DefaultViewHolder


class CellListAdapter(private val listener: CellListAdapterListener) : RecyclerView.Adapter<DefaultViewHolder>() {
    private var data: List<CellItemModel>? = null

    fun setData(data: List<CellItemModel>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cell, parent, false)
        return DefaultViewHolder(view)
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val item = data?.get(position)
        val view = holder.itemView

        if (item != null) {

            view.setOnClickListener {
                item.let {
                    listener.onClick(item)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        data?.let {
            return it.size
        }
        return 0
    }
}