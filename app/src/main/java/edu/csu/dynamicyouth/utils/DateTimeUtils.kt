package edu.csu.dynamicyouth.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object DateTimeUtils {
    fun convertInstantToLocalDateTimeFormat(instant: Instant): String {
        val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(
            java.time.LocalDateTime.of(
                localDateTime.year,
                localDateTime.monthNumber,
                localDateTime.day,
                localDateTime.hour,
                localDateTime.minute,
                localDateTime.second
            )
        )
    }
}