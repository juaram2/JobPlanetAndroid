package com.example.jobplanet.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jobplanet.model.CellItemModel
import com.example.jobplanet.service.ApiClient
import com.example.jobplanet.service.JobPlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CellVM(application: Application): BaseVM(application) {
    private val api = ApiClient.client.create(JobPlanetApi::class.java)

    private val _cells = MutableLiveData<List<CellItemModel>>()
    val cells: LiveData<List<CellItemModel>> = _cells

    fun getCellItems(searchTerm: String? = null) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.Main) {
            val response = api.getCellItems()
            try {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        if (searchTerm != null) {
                            if (data.cellItems != null) {
                                val filteredData = data.cellItems.filter {
                                    it.name?.contains(searchTerm, true) == true
                                }
                                if (filteredData.isNotEmpty()) {
                                    _cells.postValue(filteredData)
                                } else {
                                    _noData.postValue(true)
                                }
                            }
                        } else {
                            data.cellItems?.let {
                                _cells.postValue(it)
                            } ?: run {
                                _noData.postValue(true)
                            }
                        }
                    }
                }
                _loading.postValue(false)
            } catch (e: Exception) {
                e.localizedMessage
                _loading.postValue(false)
            }
        }
    }
}