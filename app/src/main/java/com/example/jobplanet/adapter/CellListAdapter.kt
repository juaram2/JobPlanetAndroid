package com.example.jobplanet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.jobplanet.R
import com.example.jobplanet.adapter.holder.CellHorizontalViewHolder
import com.example.jobplanet.adapter.holder.CompanyViewHolder
import com.example.jobplanet.adapter.holder.ReviewViewHolder
import com.example.jobplanet.databinding.ItemRecyclerCellCompanyBinding
import com.example.jobplanet.databinding.ItemRecyclerCellHotizontalThemeBinding
import com.example.jobplanet.databinding.ItemRecyclerCellReviewBinding
import com.example.jobplanet.enum.CellType
import com.example.jobplanet.model.CellItemModel

class CellListAdapter(private val listener: CellListChildAdapterListener) : RecyclerView.Adapter<ViewHolder>() {
    private var data: List<CellItemModel>? = null

    fun setData(data: List<CellItemModel>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            CellType.CELL_TYPE_COMPANY.ordinal -> {
                CompanyViewHolder(ItemRecyclerCellCompanyBinding.inflate(LayoutInflater.from(parent.context)))
            }
            CellType.CELL_TYPE_HORIZONTAL_THEME.ordinal -> {
                CellHorizontalViewHolder(ItemRecyclerCellHotizontalThemeBinding.inflate(LayoutInflater.from(parent.context)))
            }
            CellType.CELL_TYPE_REVIEW.ordinal -> {
                ReviewViewHolder(ItemRecyclerCellReviewBinding.inflate(LayoutInflater.from(parent.context)))
            }
            else -> {
                CompanyViewHolder(ItemRecyclerCellCompanyBinding.inflate(LayoutInflater.from(parent.context)))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return data?.get(position)?.cellType?.ordinal ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)
        val view = holder.itemView

        item?.let {
            when(item.cellType) {
                CellType.CELL_TYPE_COMPANY -> {
                    val companyHolder = holder as CompanyViewHolder
                    companyHolder.bind(item)
                }
                CellType.CELL_TYPE_HORIZONTAL_THEME -> {
                    val horizontalHolder = holder as CellHorizontalViewHolder
                    horizontalHolder.bind(item)

                    item.recommendRecruit?.let {
                        val horizontalDataGroup = view.findViewById<RecyclerView>(R.id.recycler_view_cell_recruit)
                        val adapter = CellListChildAdapter(it, listener)
                        val layoutManager = LinearLayoutManager(horizontalDataGroup.context, LinearLayoutManager.HORIZONTAL, false)

                        horizontalDataGroup.adapter = adapter
                        horizontalDataGroup.layoutManager = layoutManager
                    }
                }
                CellType.CELL_TYPE_REVIEW -> {
                    val reviewHolder = holder as ReviewViewHolder
                    reviewHolder.bind(item)
                }
                else -> {}
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
