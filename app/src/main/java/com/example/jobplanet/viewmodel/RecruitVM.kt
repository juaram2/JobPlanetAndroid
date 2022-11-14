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

class RecruitVM(application: Application): BaseVM(application) {
    private val api = ApiClient.client.create(JobPlanetApi::class.java)

    private val _recruits = MutableLiveData<List<RecruitItemModel>>()
    val recruits: LiveData<List<RecruitItemModel>> = _recruits

    fun getRecruitItems(searchTerm: String? = null) {
        _loading.postValue(true)

        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getRecruitItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        if (searchTerm != null) {
                            if (data.recruitItems != null) {
                                val filteredData = data.recruitItems.filter {
                                    it.title?.contains(searchTerm, true) == true
                                }
                                if (filteredData.isNotEmpty()) {
                                    _recruits.postValue(filteredData)
                                } else {
                                    _noData.postValue(true)
                                }
                            }
                        } else {
                            data.recruitItems?.let {
                                _recruits.postValue(it)
                            } ?: run {
                                _noData.postValue(true)
                            }
                        }
                    }
                    _loading.postValue(false)
                }
            } catch (e: Exception) {
                e.localizedMessage
                _loading.postValue(false)
            }
        }
    }
}