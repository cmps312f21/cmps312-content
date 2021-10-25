package qu.cmps312.shoppinglist.db

import androidx.room.TypeConverter
import kotlinx.datetime.*

class DateConverter {
    // Convert from Date to a value that can be stored in SQLite Database
    @TypeConverter
    fun fromDate(date: LocalDateTime) : Long =
        date.toInstant(TimeZone.currentSystemDefault()).epochSeconds

    // Convert from date Long value read from SQLite DB to a Date value
    // that can be assigned to an entity property
    @TypeConverter
    fun toDate(dateLong: Long) : LocalDateTime =
        Instant.fromEpochSeconds(dateLong).toLocalDateTime(TimeZone.currentSystemDefault())
}