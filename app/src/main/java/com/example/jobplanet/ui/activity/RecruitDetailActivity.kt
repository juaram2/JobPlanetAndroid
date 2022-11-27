package com.example.jobplanet.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.jobplanet.R
import com.example.jobplanet.viewmodel.RecruitDetailVM

class RecruitDetailActivity : BaseActivity() {
    private val viewModel by viewModels<RecruitDetailVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit_detail)
        loadingProgress = findViewById(R.id.loading_view)

        val id = intent.getIntExtra(ID, 0)

        id.let {
            viewModel.getRecruitItem(id)
        }
    }

    companion object {
        const val ID = "id"

        fun recruitDetailIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, RecruitDetailActivity::class.java)
            intent.putExtra(ID, id)

            return intent
        }
    }
}