package g.sig.core_data.repositories

import android.content.Context
import g.sig.core_data.Response
import g.sig.core_data.Singletons
import g.sig.core_data.models.transaction.Month
import g.sig.core_data.models.transaction.MonthCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

class MonthRepo(context: Context) {
    private val monthDao = Singletons.getDatabase(context).monthDao()

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

    suspend fun getCategories(monthId: Long): Flow<Response<MonthCategories>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(monthDao.getCategories(monthId)))
        }

}