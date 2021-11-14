package com.haidev.coronavirusapp.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat

object ConverterUtils {
    fun convertToDecimal(number: Int): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        return formatter.format(number)
    }

    fun convertDatetime(s: String): String? {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("d-M-yyyy")
        return formatter.format(parser.parse(s))
    }
}