package com.example.jobplanet

import android.os.Bundle
import com.example.jobplanet.ui.activity.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadingProgress = findViewById(R.id.loading_view)
    }
}