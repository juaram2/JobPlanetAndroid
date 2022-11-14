package com.example.jobplanet.utils

import android.view.LayoutInflater
import com.example.jobplanet.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {
    companion object {
        private val decimalFormat = DecimalFormat("#,###,###")
        private val dateFormat = DateTimeFormatter.ofPattern("yyyy-mm-dd", Locale.ENGLISH)

        fun formattingDecimal(number: Int): String {
            return decimalFormat.format(number)
        }

        fun formattingDate(date: LocalDateTime): String {
            return dateFormat.format(date)
        }

        fun splitStringToArray(item: String, itemGroup: ChipGroup) {
            if (item.isNotEmpty()) {
                val chipGroupInflater = LayoutInflater.from(itemGroup.context)

                // Convert String to List<String>
                val appeals: List<String> = item.split(",")
                val children = appeals.map {
                    val chip = chipGroupInflater.inflate(R.layout.item_chip_appeal, itemGroup, false) as Chip
                    chip.text = it.trim()
                    chip
                }

                itemGroup.removeAllViews()

                for(chip: Chip in children) {
                    itemGroup.addView(chip)
                }
            }
        }
    }
}