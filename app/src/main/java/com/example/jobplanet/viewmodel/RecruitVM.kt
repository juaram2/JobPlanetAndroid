package com.example.jobplanet.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jobplanet.model.RecruitItemModel
import com.example.jobplanet.model.RecruitItemsModel
import com.example.jobplanet.service.ApiClient
import com.example.jobplanet.service.JobPlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecruitVM(application: Application): BaseVM(application) {
    private val api = ApiClient.client.create(JobPlanetApi::class.java)

    private val handler = Handler(Looper.getMainLooper())

    private val _recruits = MutableLiveData<List<RecruitItemModel>>()
    val recruits: LiveData<List<RecruitItemModel>> = _recruits

    fun noData(isEmpty: Boolean) {
        _noData.postValue(isEmpty)
    }

    fun onQueryTextSubmit(searchTerm: String) {
        getRecruitItems(searchTerm)
    }

    fun onQueryTextChange(searchTerm: String) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            viewModelScope.launch(Dispatchers.Main) {
                getRecruitItems(searchTerm)
            }
        }, 500)
    }

    fun getRecruitItems(searchTerm: String? = null) {

        Log.d("searchTerm", searchTerm.toString())
        _loading.value = true

        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getRecruitItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        if (searchTerm?.isNotEmpty() == true && searchTerm != "") {
                            val oldItems = _recruits.value?.toMutableList()

                            val filteredData = data.recruitItems?.filter {
                                it.title?.contains(searchTerm) == true
                            }

                            if (data.recruitItems != null) {
                                oldItems?.addAll(filteredData!!)
                            }

                            val newData = RecruitItemsModel(
                                recruitItems = oldItems
                            )

                            _recruits.postValue(newData.recruitItems)
                        } else {
                            _recruits.postValue(data.recruitItems)
                        }
                        Log.d("debug", "getRecruitItems count ${data.recruitItems?.size}")
                        Log.d("debug", "getRecruitItems ${data.recruitItems}")
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