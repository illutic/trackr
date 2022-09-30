package g.sig.core_data.utils

import g.sig.core_data.models.transaction.Month
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

fun Month.getTotal() = budget - expenses

fun Month.toDisplayName(): String = java.time.Month.of(month)
    .getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())

fun LocalDate.toMonthDisplayName(): String =
    month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())

fun LocalDate.toYearDisplayName() = year.toString()

fun Month.toLocalDate(): LocalDate = LocalDate.of(year, month, 1)