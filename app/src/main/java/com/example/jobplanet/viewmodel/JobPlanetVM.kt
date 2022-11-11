package com.example.jobplanet.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobplanet.model.CellItemModel
import com.example.jobplanet.model.RecruitItemModel
import com.example.jobplanet.service.ApiClient
import com.example.jobplanet.service.JobPlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobPlanetVM(application: Application): BaseVM(application) {
    private val api = ApiClient.client.create(JobPlanetApi::class.java)

    private val handler = Handler(Looper.getMainLooper())

    private val _recruits = MutableLiveData<List<RecruitItemModel>>()
    val recruits: LiveData<List<RecruitItemModel>> = _recruits

    private val _cells = MutableLiveData<List<CellItemModel>>()
    val cells: LiveData<List<CellItemModel>> = _cells

    fun noData(isEmpty: Boolean) {
        _noData.postValue(isEmpty)
    }

    fun onQueryTextSubmit(searchTerm: String) {
        getRecruitItems(searchTerm)
        getCellItems(searchTerm)
    }

    fun onQueryTextChange(searchTerm: String) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            viewModelScope.launch(Dispatchers.Main) {
                getRecruitItems(searchTerm)
                getCellItems(searchTerm)
            }
        }, 500)
    }

    fun getRecruitItems(searchTerm: String? = null) {
        _loading.value = true

        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getRecruitItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        if (!searchTerm.isNullOrBlank()) {
                            val filteredData = data.recruitItems?.filter {
                                it.title?.contains(searchTerm) == true
                            }
                            _recruits.postValue(filteredData)
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

    fun getCellItems(searchTerm: String? = null) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getCellItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        if (!searchTerm.isNullOrBlank()) {
                            val filteredData = data.cellItems?.filter {
                                it.name?.contains(searchTerm) == true
                            }
                            _cells.postValue(filteredData)
                        } else {
                            _cells.postValue(data.cellItems)
                        }
                        Log.d("debug", "getCellItems count ${data.cellItems?.size}")
                        Log.d("debug", "getCellItems ${data.cellItems}")
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