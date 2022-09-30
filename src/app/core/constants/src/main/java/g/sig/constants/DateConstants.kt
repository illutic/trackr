package g.sig.constants

import java.time.LocalDate
import java.time.Month

object DateConstants {
    val Months = Month.values().toList()
    val Years = (LocalDate.now().minusYears(20).year..LocalDate.now().year).toList()
}