package g.sig.core_data.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.format.DateTimeParseException

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: String?): Instant? = try {
        Instant.parse(value)
    } catch (e: DateTimeParseException) {
        null
    }

    @TypeConverter
    fun instantToTimestamp(value: Instant?): String? = value?.toString()
}