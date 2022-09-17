package g.sig.core_data.repositories

import android.content.Context
import g.sig.core_data.DataInterface
import g.sig.core_data.Response
import g.sig.core_data.Singletons
import g.sig.core_data.models.transaction.Month
import g.sig.core_data.models.transaction.MonthCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow

class MonthRepo(context: Context) : DataInterface.MonthInterface {
    private val monthDao = Singletons.getDatabase(context).monthDao()

    override suspend fun setMonth(month: Month): Flow<Response<Boolean>> =
        channelFlow {
            send(Response.Loading)
            monthDao.setMonth(month)
            send(Response.Success(true))
        }

    override suspend fun getBudget(monthId: Int): Flow<Response<Double>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(monthDao.getBudget(monthId)))
        }

    override suspend fun getExpenses(monthId: Int): Flow<Response<Double>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(monthDao.getExpenses(monthId)))
        }

    override suspend fun getCategories(monthId: Int): Flow<Response<MonthCategories>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(monthDao.getCategories(monthId)))
        }

}