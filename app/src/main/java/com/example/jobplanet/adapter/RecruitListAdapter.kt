package com.example.jobplanet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jobplanet.R
import com.example.jobplanet.adapter.holder.CompanyViewHolder
import com.example.jobplanet.adapter.holder.RecruitViewHolder
import com.example.jobplanet.databinding.FragmentRecruitBinding
import com.example.jobplanet.databinding.ItemRecyclerRecruitBinding
import com.example.jobplanet.model.RecruitItemModel
import com.example.jobplanet.utils.Utils
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class RecruitListAdapter(private val listener: RecruitListAdapterListener) : RecyclerView.Adapter<RecruitViewHolder>() {
    private var data: List<RecruitItemModel>? = null

    fun setData(data: List<RecruitItemModel>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecruitViewHolder {
        return RecruitViewHolder(ItemRecyclerRecruitBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecruitViewHolder, position: Int) {
        val item = data?.get(position)
        val view = holder.itemView

        item?.let {
            holder.bind(it)

            /**
             * Appeal
             */
            val appealGroup = view.findViewById<ChipGroup>(R.id.appeal_group)
            if (!item.appeal.isNullOrEmpty()) {
                val chipGroupInflater = LayoutInflater.from(appealGroup.context)

                // Convert String to List<String>
                val appeals = Utils.stringToList(item.appeal)
                val children = appeals.map {
                    val chip = chipGroupInflater.inflate(R.layout.item_chip_appeal, appealGroup, false) as Chip
                    chip.text = it.trim()
                    chip
                }

                appealGroup.removeAllViews()

                for(chip: Chip in children) {
                    appealGroup.addView(chip)
                }
            }

            /**
             * Navigation
             */
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