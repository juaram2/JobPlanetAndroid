package com.example.jobplanet.ui.activity

import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity() : AppCompatActivity() {
    var loadingProgress: ProgressBar? = null

    fun showProgressView() {
        loadingProgress?.let {
            setUntouchable()
            it.visibility = View.VISIBLE
        }
    }

    fun hideProgressView() {
        loadingProgress?.let {
            it.visibility = View.GONE
            setTouchable()
        }
    }

    private fun setTouchable() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private fun setUntouchable() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}
