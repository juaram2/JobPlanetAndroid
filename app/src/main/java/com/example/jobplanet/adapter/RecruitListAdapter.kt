package com.example.jobplanet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobplanet.R
import com.example.jobplanet.model.RecruitItemModel
import com.example.jobplanet.utils.DefaultViewHolder
import com.example.jobplanet.utils.Utils
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class RecruitListAdapter(private val listener: RecruitListAdapterListener) : RecyclerView.Adapter<DefaultViewHolder>() {
    private var data: List<RecruitItemModel>? = null

    fun setData(data: List<RecruitItemModel>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recruit, parent, false)
        return DefaultViewHolder(view)
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val item = data?.get(position)
        val view = holder.itemView

        val thumbnail = view.findViewById<ImageView>(R.id.thumbnail)
        val title = view.findViewById<TextView>(R.id.title)
        val companyRating = view.findViewById<TextView>(R.id.company_rating)
        val companyName = view.findViewById<TextView>(R.id.company_name)
        val appealGroup = view.findViewById<ChipGroup>(R.id.appeal_group)
        val reward = view.findViewById<TextView>(R.id.reward)

        item?.let {
            /**
             * Thumbnail
             */
            if (!item.imageUrl.isNullOrEmpty()) {
                Glide.with(view).load(item.imageUrl).centerCrop().into(thumbnail)
            }

            /**
             * Title
             */
            title.text = item.title ?: ""

            /**
             * Company
             */
            if (item.company != null) {
                val highestRating = item.company.ratings?.maxOf { it.rating!! }
                companyRating.text = highestRating.toString()
                companyName.text = item.company.name ?: ""
            }

            /**
             * Appeal
             */
            if (!item.appeal.isNullOrEmpty()) {
                val chipGroupInflater = LayoutInflater.from(appealGroup.context)

                // Convert String to List<String>
                val appeals: List<String> = item.appeal.split(",")
                val children = appeals.map {
                    val chip = chipGroupInflater.inflate(R.layout.item_appeal, appealGroup, false) as Chip
                    chip.text = it.trim()
                    chip
                }

                appealGroup.removeAllViews()

                for(chip: Chip in children) {
                    appealGroup.addView(chip)
                }
            }

            /**
             * Reward
             */
            if (item.reward != null && item.reward != 0) {
                val formattedReward = Utils.formattingDecimal(item.reward)
                reward.text = "보상금: " + formattedReward + "원"
            } else {
                reward.text = ""
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