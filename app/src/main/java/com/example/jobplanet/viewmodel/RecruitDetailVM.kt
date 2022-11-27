package com.example.jobplanet.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jobplanet.model.RecruitItemModel
import com.example.jobplanet.service.ApiClient
import com.example.jobplanet.service.JobPlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecruitDetailVM(application: Application) : BaseVM(application) {
    private val api = ApiClient.client.create(JobPlanetApi::class.java)

    private val _recruit = MutableLiveData<RecruitItemModel>()
    val recruit: LiveData<RecruitItemModel> = _recruit

    fun getRecruitItem(id: Int) {
        _loading.value = true

        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getRecruitItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        val findData = data.recruitItems?.find {
                            id == it.id
                        }
                        _recruit.postValue(findData)
                    }
                }
                _loading.value = false
            } catch (e: Exception) {
                e.localizedMessage
                _loading.value = false
            }
        }
    }
}