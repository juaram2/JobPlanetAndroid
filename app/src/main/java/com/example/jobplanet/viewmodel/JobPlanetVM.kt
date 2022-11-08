package com.example.jobplanet.viewmodel

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

class JobPlanetVM: ViewModel() {
    private val api = ApiClient.client.create(JobPlanetApi::class.java)

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _recruits = MutableLiveData<List<RecruitItemModel>>()
    val recruits: LiveData<List<RecruitItemModel>> = _recruits

    private val _cells = MutableLiveData<List<CellItemModel>>()
    val cells: LiveData<List<CellItemModel>> = _cells

    fun getRecruits() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getRecruitItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _recruits.postValue(it)
                    }
                } else {
                    Log.d("debug", "getRecruits failed.")
                }
                _loading.value = false
            } catch (e: Exception) {
                Log.e("error", "Error! ${e.localizedMessage}")
                _loading.value = false
            }
        }
    }

    fun getCells() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getCellItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _cells.postValue(it)
                    }
                } else {
                    Log.d("debug", "getRecruits failed.")
                }
                _loading.value = false
            } catch (e: Exception) {
                Log.e("error", "Error! ${e.localizedMessage}")
                _loading.value = false
            }
        }
    }
}