package edu.csu.dynamicyouth.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

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

    fun Duration.toMmSs(): String {
        val minutes = this.inWholeMinutes
        val seconds = (this - minutes.minutes).inWholeSeconds
        return "%02d′ %02d″".format(minutes, seconds)
    }
}