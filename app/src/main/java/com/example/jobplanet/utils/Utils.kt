package com.example.jobplanet.utils

import java.text.DecimalFormat

class Utils {
    companion object {
        val decimalFormat = DecimalFormat("#,###,###")

        fun formattingDecimal(number: Int): String {
            return decimalFormat.format(number)
        }
    }
}