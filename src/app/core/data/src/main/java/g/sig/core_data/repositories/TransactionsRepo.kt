package g.sig.core_data.repositories

import android.content.Context
import g.sig.core_data.DataInterface
import g.sig.core_data.Response
import g.sig.core_data.Singletons
import g.sig.core_data.models.transaction.LoggedTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class TransactionsRepo(context: Context) : DataInterface.TransactionInterface {
    private val transactionsDao = Singletons.getDatabase(context).transactionsDao()

    override suspend fun setTransaction(loggedTransaction: LoggedTransaction): Flow<Response<Boolean>> =
        channelFlow {
            send(Response.Loading)
            transactionsDao.setTransaction(loggedTransaction)
            send(Response.Success(true))
        }

    override suspend fun getTransaction(loggedTransactionId: Int): Flow<Response<LoggedTransaction>> =
        channelFlow {
            send(Response.Loading)
            transactionsDao.getTransaction(loggedTransactionId)?.let {
                send(Response.Success(it))
            } ?:
            send(Response.Error(""))
        }

    override suspend fun deleteTransaction(loggedTransactionId: Int): Flow<Response<Boolean>> =
        channelFlow {
            getTransaction(loggedTransactionId).collectLatest {
                when (it) {
                    is Response.Loading -> send(Response.Loading)
                    is Response.Error -> send(Response.Error(it.errorMessage))
                    is Response.Success -> {
                        transactionsDao.deleteTransaction(it.data)
                        send(Response.Success(true))
                    }
                }
            }
        }
}