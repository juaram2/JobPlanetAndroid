package com.example.jobplanet.model

import com.example.jobplanet.enum.CellType
import com.squareup.moshi.Json
import java.time.LocalDateTime

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
    val updateDate: LocalDateTime?,
)