package qu.cmps312.shoppinglist.db

import androidx.room.TypeConverter
import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.util.*

class DateTimeConverter {
    // Convert from Date to a value that can be stored in SQLite Database
    @TypeConverter
    fun fromDate(date: LocalDate) : Long =
        date.atTime(0,0,0 ).toInstant(TimeZone.currentSystemDefault()).epochSeconds

    // Convert from date Long value read from SQLite DB to a Date value
    // that can be assigned to an entity property
    @TypeConverter
    fun toDate(dateLong: Long) : LocalDate =
        Instant.fromEpochSeconds(dateLong).toLocalDateTime(TimeZone.currentSystemDefault()).date
}

class DateConverter {
    // Convert from Dote to a value that con be stored in SQLite Database
    @TypeConverter
    fun toLong(date: Date) : Long = date.time
    // Convert from dote Long value read from SQLite DB to a Date value // that can be assigned to an entity property
    @TypeConverter
    fun toDate(dateLong: Long) : Date = Date(dateLong)
}