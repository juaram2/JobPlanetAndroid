package com.example.jobplanet.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

abstract class BaseVM(application: Application) : AndroidViewModel(application) {

    protected val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    protected val _noData = MutableLiveData(false)
    val noData: LiveData<Boolean> = _noData
}