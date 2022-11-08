package com.example.jobplanet.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobplanet.model.CellItemsModel
import com.example.jobplanet.model.RecruitItemsModel
import com.example.jobplanet.service.ApiClient
import com.example.jobplanet.service.JobPlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobPlanetVM: ViewModel() {
    private val api = ApiClient.client.create(JobPlanetApi::class.java)

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _recruits = MutableLiveData<RecruitItemsModel>()
    val recruits: LiveData<RecruitItemsModel> = _recruits

    private val _cells = MutableLiveData<CellItemsModel>()
    val cells: LiveData<CellItemsModel> = _cells

    fun getRecruitItems() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getRecruitItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _recruits.postValue(it)
                        Log.d("debug", "getRecruits ${it.recruitItems}")
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

    fun getCellItems() {
        _loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getCellItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _cells.postValue(it)
                        Log.d("debug", "getRecruits ${it.cellItems}")
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