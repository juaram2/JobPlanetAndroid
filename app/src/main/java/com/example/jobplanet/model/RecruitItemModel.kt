package com.example.jobplanet.model

import com.squareup.moshi.Json

data class RecruitItemsModel(
    @Json(name = "recruit_items")
    val recruitItems: List<RecruitItemModel>? = null,
)

data class RecruitItemModel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "reward")
    val reward: Int? = null,
    @Json(name = "appeal")
    val appeal: String? = null,
    @Json(name = "image_url")
    val imageUrl: String? = null,
    @Json(name = "company")
    val company: CompanyModel? = null
)

data class CompanyModel(
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "logo_path")
    val logoPath: String? = null,
    @Json(name = "ratings")
    val ratings: List<RatingModel>? = null
)

data class RatingModel(
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "rating")
    val rating: Double? = null
)