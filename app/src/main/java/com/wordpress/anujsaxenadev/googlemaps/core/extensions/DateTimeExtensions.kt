package com.wordpress.anujsaxenadev.googlemaps.core.extensions

import java.util.Calendar

fun getTodayTimeStamp(): Long{
    return try {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        calendar.timeInMillis
    }
    catch (e: Exception){
        0
    }
}