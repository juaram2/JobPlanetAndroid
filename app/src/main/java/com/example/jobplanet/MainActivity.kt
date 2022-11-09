package com.example.jobplanet

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.jobplanet.ui.activity.BaseActivity
import com.example.jobplanet.viewmodel.JobPlanetVM

class MainActivity : BaseActivity() {
    private val viewModel by viewModels<JobPlanetVM>()

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        // Loading View
        viewModel.loading.observe(this) {
            if (it) {
                showProgressView()
            } else {
                hideProgressView()
            }
        }
    }
}