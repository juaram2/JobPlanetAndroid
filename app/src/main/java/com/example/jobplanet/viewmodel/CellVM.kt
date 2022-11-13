package com.example.jobplanet.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jobplanet.enum.CellType
import com.example.jobplanet.model.CellItemModel
import com.example.jobplanet.model.RecruitItemModel
import com.example.jobplanet.service.ApiClient
import com.example.jobplanet.service.JobPlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CellVM(application: Application): BaseVM(application) {
    private val api = ApiClient.client.create(JobPlanetApi::class.java)

    private val handler = Handler(Looper.getMainLooper())

    private val _cells = MutableLiveData<List<CellItemModel>>()
    val cells: LiveData<List<CellItemModel>> = _cells

    fun noData(isEmpty: Boolean) {
        _noData.postValue(isEmpty)
    }

    fun onQueryTextSubmit(searchTerm: String) {
        getCellItems(searchTerm)
    }

    fun onQueryTextChange(searchTerm: String) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed({
            viewModelScope.launch(Dispatchers.Main) {
                getCellItems(searchTerm)
            }
        }, 500)
    }

    fun getCellItems(searchTerm: String? = null) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getCellItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        if (!searchTerm.isNullOrBlank()) {
//                            val filteredData = data.cellItems?.filter {
//                                it.name?.contains(searchTerm) == true
//                            }
//                            _cells.postValue(filteredData)
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