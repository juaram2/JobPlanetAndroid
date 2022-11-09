package com.example.jobplanet.viewmodel

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

class JobPlanetVM: ViewModel() {
    private val api = ApiClient.client.create(JobPlanetApi::class.java)

    private val handler = Handler(Looper.getMainLooper())

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _recruits = MutableLiveData<List<RecruitItemModel>?>()
    val recruits: LiveData<List<RecruitItemModel>?> = _recruits

    private val _cells = MutableLiveData<List<CellItemModel>?>()
    val cells: LiveData<List<CellItemModel>?> = _cells

    fun onQueryTextSubmit(searchTerm: String?) {
        searchTerm?.let { searchTerm ->
            getRecruitItems(searchTerm)
            getCellItems(searchTerm)
        }
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
                        searchTerm?.let { term ->
                            val filteredData = data.recruitItems?.filter {
                                term.contains(it.title ?: "")
                            }
                            _recruits.postValue(filteredData)
                        } ?: run {
                            _recruits.postValue(data.recruitItems)
                        }
                        Log.d("debug", "getRecruits ${data.recruitItems}")
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

    fun getCellItems(searchTerm: String? = null) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getCellItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        searchTerm?.let { term ->
                            val filteredData = data.cellItems?.filter {
                                term.contains(it.name ?: "")
                            }
                            _cells.postValue(filteredData)
                        } ?: run {
                            _cells.postValue(data.cellItems)
                        }
                        Log.d("debug", "getRecruits ${data.cellItems}")
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