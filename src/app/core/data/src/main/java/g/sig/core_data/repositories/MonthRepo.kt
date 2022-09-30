package g.sig.core_data.repositories

import android.content.Context
import g.sig.core_data.Response
import g.sig.core_data.Singletons
import g.sig.core_data.models.transaction.Month
import g.sig.core_data.models.transaction.MonthCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.temporal.ChronoField
import java.time.temporal.IsoFields
import java.time.temporal.Temporal
import java.time.temporal.TemporalField
import java.time.temporal.TemporalQueries
import java.util.*

class MonthRepo(context: Context) {
    private val monthDao = Singletons.getDatabase(context).monthDao()

    suspend fun getMonth(monthId: Long): Flow<Response<MonthCategories>> =
        channelFlow {
            send(Response.Loading)
            val month = monthDao.getMonth(monthId)
            send(Response.Success(month))
        }

    suspend fun getMonth(date: LocalDate): Flow<Response<MonthCategories>> =
        channelFlow {
            send(Response.Loading)
            val monthDate: Int = date.month.value
            val year: Int = date.year
            val month = monthDao.getMonthByDate(monthDate, year)
            send(Response.Success(month))
        }

    suspend fun setMonth(month: Month): Flow<Response<Boolean>> =
        channelFlow {
            send(Response.Loading)
            monthDao.setMonth(month)
            send(Response.Success(true))
        }

    suspend fun getBudget(monthId: Long): Flow<Response<Double>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(monthDao.getBudget(monthId)))
        }

    suspend fun getExpenses(monthId: Long): Flow<Response<Double>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(monthDao.getExpenses(monthId)))
        }

}