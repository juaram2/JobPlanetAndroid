package com.example.jobplanet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobplanet.R
import com.example.jobplanet.model.RecruitItemModel


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

        if (item != null) {
            if (item.imageUrl != null) {
                Glide.with(view).load(item.imageUrl).centerCrop().into(thumbnail)
            }
            title.text = item.title ?: ""

            if (item.company != null) {
                val highestRating = item.company.ratings?.maxOf { it.rating!! }
                companyRating.text = highestRating.toString()
                companyName.text = item.company.name ?: ""
            }

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