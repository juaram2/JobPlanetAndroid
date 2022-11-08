package com.example.jobplanet.model

import com.squareup.moshi.Json

data class RecruitItemModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String?,
    @Json(name = "reward")
    val reward: Int?,
    @Json(name = "appeal")
    val appeal: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "company")
    val company: CompanyModel?
)

data class CompanyModel(
    @Json(name = "name")
    val name: String?,
    @Json(name = "logo_path")
    val logoPath: String?,
    @Json(name = "ratings")
    val ratings: List<RatingModel>?
)

data class RatingModel(
    @Json(name = "type")
    val type: String?,
    @Json(name = "rating")
    val rating: Double?
)