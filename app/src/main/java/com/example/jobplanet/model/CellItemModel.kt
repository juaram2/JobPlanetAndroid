package com.example.jobplanet.model

import com.example.jobplanet.enum.CellType
import com.squareup.moshi.Json
import java.time.LocalDateTime

data class CellItemsModel (
    @Json(name = "cell_items")
    val cellItems: List<CellItemModel>?
)

data class CellItemModel (
    @Json(name = "cell_type")
    val cellType: CellType?,
    @Json(name = "count")
    val count: Int?,
    @Json(name = "section_title")
    val sectionTitle: String?,
    @Json(name = "recommend_recruit")
    val recommendRecruit: List<RecruitItemModel>?,
    @Json(name = "logo_path")
    val logoPath: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "industry_name")
    val industryName: String?,
    @Json(name = "rate_total_avg")
    val rateTotalAvg: Double?,
    @Json(name = "review_summary")
    val reviewSummary: String?,
    @Json(name = "cons")
    val cons: String?,
    @Json(name = "pros")
    val pros: String?,
    @Json(name = "interview_question")
    val interviewQuestion: String?,
    @Json(name = "salary_avg")
    val salaryAvg: Int?,
    @Json(name = "update_date")
    val updateDate: LocalDateTime?
)

data class CellCompanyModel (
    @Json(name = "cell_type")
    val cellType: CellType = CellType.CELL_TYPE_COMPANY,
    @Json(name = "logo_path")
    val logoPath: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "industry_name")
    val industryName: String? = null,
    @Json(name = "rate_total_avg")
    val rateTotalAvg: Double? = null,
    @Json(name = "review_summary")
    val reviewSummary: String? = null,
    @Json(name = "interview_question")
    val interviewQuestion: String? = null,
    @Json(name = "salary_avg")
    val salaryAvg: Int? = null,
    @Json(name = "update_date")
    val updateDate: LocalDateTime? = null
)

data class CellHorizontalThemeModel (
    @Json(name = "cell_type")
    val cellType: CellType = CellType.CELL_TYPE_HORIZONTAL_THEME,
    @Json(name = "count")
    val count: Int? = null,
    @Json(name = "section_title")
    val sectionTitle: String? = null,
    @Json(name = "recommend_recruit")
    val recommendRecruit: List<RecruitItemModel>? = null
)

data class CellReviewModel (
    @Json(name = "cell_type")
    val cellType: CellType = CellType.CELL_TYPE_REVIEW,
    @Json(name = "logo_path")
    val logoPath: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "industry_name")
    val industryName: String? = null,
    @Json(name = "rate_total_avg")
    val rateTotalAvg: Double? = null,
    @Json(name = "review_summary")
    val reviewSummary: String? = null,
    @Json(name = "cons")
    val cons: String? = null,
    @Json(name = "pros")
    val pros: String? = null,
    @Json(name = "update_date")
    val updateDate: LocalDateTime? = null
)