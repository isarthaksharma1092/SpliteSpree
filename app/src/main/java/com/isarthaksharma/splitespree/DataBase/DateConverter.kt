package com.isarthaksharma.splitespree.DataBase;

import androidx.room.TypeConverters
import java.util.Date;

class DateConverter {
    @TypeConverters
    fun fromDate(date:Date):Long{
        return date.time
    }
    @TypeConverters
    fun toDate(time: Long):Date {
        return Date(time)
    }
}
