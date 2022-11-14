package com.example.jobplanet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jobplanet.R
import com.example.jobplanet.enum.CellType
import com.example.jobplanet.model.CellItemModel
import com.example.jobplanet.utils.DefaultViewHolder
import com.example.jobplanet.utils.Utils

class CellListAdapter(private val listener: CellListChildAdapterListener) : RecyclerView.Adapter<DefaultViewHolder>() {
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
        val logo: ImageView = view.findViewById(R.id.logo)
        val name: TextView = view.findViewById(R.id.name)
        val rateTotalAvg: TextView = view.findViewById(R.id.rate_avg)
        val industry: TextView = view.findViewById(R.id.industry)
        val iconQuote: ImageView = view.findViewById(R.id.icon_quote)
        val updateDate: TextView = view.findViewById(R.id.update_date)
        val reviewSummary: TextView = view.findViewById(R.id.review_summary)
        val cons: TextView = view.findViewById(R.id.cons)
        val pros: TextView = view.findViewById(R.id.pros)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        return when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_cell_company, parent, false)
                CompanyTypeViewHolder(view)
            }
            1 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_cell_hotizontal_theme, parent, false)
                HorizontalThemeTypeViewHolder(view)
            }
            2 -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_cell_review, parent, false)
                ReviewTypeViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_cell_company, parent, false)
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

                        val adapter = CellListChildAdapter(item.recommendRecruit, listener)
                        val layoutManager = LinearLayoutManager(horizontalDataGroup.context, LinearLayoutManager.HORIZONTAL, false)

                        horizontalDataGroup.adapter = adapter
                        horizontalDataGroup.layoutManager = layoutManager
                    }
                }
                CellType.CELL_TYPE_REVIEW -> {
                    val reviewHolder = ReviewTypeViewHolder(view)
                    /**
                     * Logo
                     */
                    Glide.with(view)
                        .load(item.logoPath)
                        .error(R.drawable.placeholder)
                        .centerCrop()
                        .into(reviewHolder.logo)

                    /**
                     * Name
                     */
                    reviewHolder.name.text = item.name

                    /**
                     * Rate Total Avg
                     */
                    reviewHolder.rateTotalAvg.text = item.rateTotalAvg.toString()
                    reviewHolder.industry.text = item.industryName

                    /**
                     * Review Summary
                     */
                    if (!item.reviewSummary.isNullOrEmpty()) {
                        reviewHolder.iconQuote.visibility = View.VISIBLE
                        item.updateDate?.let {
                            reviewHolder.updateDate.text = Utils.formattingDate(item.updateDate)
                        }
                        reviewHolder.reviewSummary.text = item.reviewSummary
                    } else {
                        reviewHolder.iconQuote.visibility = View.GONE
                    }

                    /**
                     * Cons
                     */
                    reviewHolder.cons.text = item.cons

                    /**
                     * Pros
                     */
                    reviewHolder.pros.text = item.pros
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
