package com.example.jobplanet.service

import com.example.jobplanet.model.CellItemModel
import com.example.jobplanet.model.RecruitItemModel
import retrofit2.Response
import retrofit2.http.GET

interface JobPlanetApi {
    @GET("/mobile-config/test_data_recruit_items.json")
    suspend fun getRecruitItems(): Response<List<RecruitItemModel>>

    @GET("/mobile-config/test_data_cell_items.json")
    suspend fun getCellItems(): Response<List<CellItemModel>>
}