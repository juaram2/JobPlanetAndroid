package com.example.jobplanet.utils

class Utils {
    companion object {
        fun stringToList(string: String): List<String> {
            return string.split(",")
        }
    }
}