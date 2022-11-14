package com.example.jobplanet.utils

import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.jobplanet.R
import com.example.jobplanet.model.RatingModel
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {
    companion object {
        private val decimalFormat = DecimalFormat("#,###,###")
        @RequiresApi(Build.VERSION_CODES.O)
        private val dateFormat = DateTimeFormatter.ofPattern("yyyy-mm-dd", Locale.ENGLISH)

        fun formattingDecimal(number: Int): String {
            return decimalFormat.format(number)
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun formattingDate(date: LocalDateTime): String {
            return dateFormat.format(date)
        }

        fun stringToList(string: String): List<String>{
            return string.split(",")
        }

        fun highestNumber(numbers: List<RatingModel>?): String {
            val number = numbers?.maxOf { it.rating!! }
            return number.toString()
        }

        fun imageView(fragment: Fragment, url: String?, imageView: ImageView) {
            Glide
                .with(fragment)
                .load(url)
                .error(R.drawable.placeholder)
                .centerCrop()
                .into(imageView)
        }
    }
}