package com.example.jobplanet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobplanet.R
import com.example.jobplanet.enum.CellType
import com.example.jobplanet.model.CellItemModel
import com.example.jobplanet.utils.DefaultViewHolder
import com.example.jobplanet.utils.Utils
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class CellListAdapter(private val listener: CellListAdapterListener) : RecyclerView.Adapter<DefaultViewHolder>() {
    private var data: List<CellItemModel>? = null

    fun setData(data: List<CellItemModel>) {
        this.data = data
    }

    class CompanyTypeViewHolder(view: View) : DefaultViewHolder(view) {
        val logo: ImageView = view.findViewById(R.id.logo)
        val name: TextView = view.findViewById(R.id.name)
        val rateTotalAvg: TextView = view.findViewById(R.id.rate_avg)
        val industry: TextView = view.findViewById(R.id.industry)
        val iconQuote: ImageView = view.findViewById(R.id.icon_quote)
        val updateDate: TextView = view.findViewById(R.id.update_date)
        val reviewSummary: TextView = view.findViewById(R.id.review_summary)
        val salaryAvg: TextView = view.findViewById(R.id.salary_avg)
        val interviewQuestion: TextView = view.findViewById(R.id.interview_question)
    }

    class HorizontalThemeTypeViewHolder(view: View) : DefaultViewHolder(view) {
        val sectionTitle: TextView = view.findViewById(R.id.section_title)
        val horizontalList: RecyclerView = view.findViewById(R.id.recycler_view_cell_recruit)
    }

    class ReviewTypeViewHolder(view: View) : DefaultViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cell_company, parent, false)
                CompanyTypeViewHolder(view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cell_hotizontal_theme, parent, false)
                HorizontalThemeTypeViewHolder(view)
            }
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cell_review, parent, false)
                ReviewTypeViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cell_company, parent, false)
                CompanyTypeViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (data?.get(position)?.cellType) {
            CellType.CELL_TYPE_COMPANY -> {
                0
            }
            CellType.CELL_TYPE_HORIZONTAL_THEME -> {
                1
            }
            CellType.CELL_TYPE_REVIEW -> {
                2
            }
            else -> {
                0
            }
        }
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val item = data?.get(position)
        val view = holder.itemView

        item?.let {
            when (it.cellType) {
                CellType.CELL_TYPE_COMPANY -> {
                    val companyHolder = CompanyTypeViewHolder(view)
                    /**
                     * Logo
                     */
                    Glide.with(view)
                        .load(item.logoPath)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(companyHolder.logo)

                    /**
                     * Name
                     */
                    companyHolder.name.text = item.name

                    /**
                     * Rate Total Avg
                     */
                    companyHolder.rateTotalAvg.text = item.rateTotalAvg.toString()
                    companyHolder.industry.text = item.industryName

                    /**
                     * Review Summary
                     */
                    if (!item.reviewSummary.isNullOrEmpty()) {
                        companyHolder.iconQuote.visibility = View.VISIBLE
                        item.updateDate?.let {
                            companyHolder.updateDate.text = Utils.formattingDate(item.updateDate)
                        }
                        companyHolder.reviewSummary.text = item.reviewSummary
                    } else {
                        companyHolder.iconQuote.visibility = View.GONE
                    }

                    /**
                     * Salary Avg
                     */
                    companyHolder.salaryAvg.text = Utils.formattingDecimal(item.salaryAvg ?: 0)

                    /**
                     * Interview question
                     */
                    companyHolder.interviewQuestion.text = item.interviewQuestion
                }
                CellType.CELL_TYPE_HORIZONTAL_THEME -> {
                    val horizontalHolder = HorizontalThemeTypeViewHolder(view)
                    horizontalHolder.sectionTitle.text = item.sectionTitle

                    item.recommendRecruit?.let {
                        val horizontalDataGroup = horizontalHolder.horizontalList
                        horizontalDataGroup.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)

                        val layoutInflater = LayoutInflater.from(horizontalDataGroup.context)
                        val horizontalData = layoutInflater.inflate(R.layout.item_recruit, horizontalDataGroup, false) as ConstraintLayout

                        horizontalData.layoutParams = ViewGroup.LayoutParams(154, ConstraintLayout.LayoutParams.WRAP_CONTENT)
                        val thumbnail = horizontalData.findViewById<ImageView>(R.id.thumbnail)
                        val title = horizontalData.findViewById<TextView>(R.id.title)
                        val companyRating = horizontalData.findViewById<TextView>(R.id.company_rating)
                        val companyName = horizontalData.findViewById<TextView>(R.id.company_name)
                        val appealGroup = horizontalData.findViewById<ChipGroup>(R.id.appeal_group)
                        val reward = horizontalData.findViewById<TextView>(R.id.reward)

                        val children = item.recommendRecruit.map { child ->
                            /**
                             * Thumbnail
                             */
                            if (!child.imageUrl.isNullOrEmpty()) {
                                Glide.with(view)
                                    .load(child.imageUrl)
                                    .error(R.drawable.placeholder)
                                    .centerCrop()
                                    .into(thumbnail)
                            }

                            /**
                             * Title
                             */
                            title.text = child.title ?: ""

                            /**
                             * Company
                             */
                            if (child.company != null) {
                                val highestRating = child.company.ratings?.maxOf { it.rating!! }
                                companyRating.text = highestRating.toString()
                                companyName.text = child.company.name ?: ""
                            }

                            /**
                             * Appeal
                             */
                            if (!child.appeal.isNullOrEmpty()) {
                                Utils.splitStringToArray(child.appeal, appealGroup)
                                val chipGroupInflater = LayoutInflater.from(appealGroup.context)

                                // Convert String to List<String>
                                val appeals: List<String> = child.appeal.split(",")
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
                            if (child.reward != null && child.reward != 0) {
                                val formattedReward = Utils.formattingDecimal(child.reward)
                                reward.text = "보상금: " + formattedReward + "원"
                            } else {
                                reward.text = ""
                            }

                            /**
                             * Navigation
                             */
    //                        view.setOnClickListener {
    //                            item.let {
    //                                listener?.onClick(item)
    //                            }
    //                        }

                            horizontalData
                        }

                        horizontalDataGroup.removeAllViews()

                        children.let {
                            for(child: ConstraintLayout in children) {
                                horizontalDataGroup.addView(child)
                            }
                        }
                    }
                }
                CellType.CELL_TYPE_REVIEW -> {}
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
