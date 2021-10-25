package qu.cmps312.shoppinglist.db

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    // Convert from Date to a value that can be stored in SQLite Database
    @TypeConverter
    fun fromDate(date: Date) : Long = date.time

    // Convert from date Long value read from SQLite DB to a Date value
    // that can be assigned to an entity property
    @TypeConverter
    fun toDate(dateLong: Long) : Date = Date(dateLong)
}