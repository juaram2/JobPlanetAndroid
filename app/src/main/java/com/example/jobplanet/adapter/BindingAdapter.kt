package com.example.jobplanet.adapter

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.jobplanet.R
import com.example.jobplanet.model.RatingModel
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


object BindingAdapter {
    private val decimalFormat = DecimalFormat("#,###,###")

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormat = DateTimeFormatter.ofPattern("yyyy-mm-dd", Locale.ENGLISH)

    @JvmStatic
    @androidx.databinding.BindingAdapter("double")
    fun setDecimal(textView: TextView, number: Int) {
        textView.text = decimalFormat.format(number)
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("datetime")
    @RequiresApi(Build.VERSION_CODES.O)
    fun setDate(textView: TextView, date: LocalDateTime) {
        textView.text = dateFormat.format(date)
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("rating")
    fun setHighestNumber(textView: TextView, numbers: List<RatingModel>?) {
        val numberMap = numbers?.map { number -> number.rating }
        val sortedNumber = numberMap?.sortedByDescending { it }
        val highestNumber = sortedNumber?.get(0) ?: 0.0

        textView.text = highestNumber.toString()
    }

    @JvmStatic
    @androidx.databinding.BindingAdapter("image")
    fun loadImage(imageView: ImageView, url: String?) {
        url?.let {
            Glide
                .with(imageView)
                .load(it)
                .error(R.drawable.placeholder)
                .centerCrop()
                .into(imageView)
        }
    }
}