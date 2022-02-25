package com.kwanso.grocerylist.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object{
        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US)
            return format.format(date)
        }
    }

}