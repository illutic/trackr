package g.sig.core_data.repositories

import android.content.Context
import g.sig.core_data.Response
import g.sig.core_data.Singletons
import g.sig.core_data.models.transaction.CategoryTransactionsCrossRef
import g.sig.core_data.models.transaction.LoggedTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

class TransactionsRepo(context: Context) {
    private val transactionsDao = Singletons.getDatabase(context).transactionsDao()

    suspend fun setTransaction(loggedTransaction: LoggedTransaction): Flow<Response<Long>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(transactionsDao.setTransaction(loggedTransaction)))
        }

    suspend fun getTransaction(loggedTransactionId: Long): Flow<Response<LoggedTransaction>> =
        channelFlow {
            send(Response.Loading)
            transactionsDao.getTransaction(loggedTransactionId)?.let {
                send(Response.Success(it))
            } ?: send(Response.Error(""))
        }

    suspend fun setTransactionCategoryRelation(transactionId: Long?, categoryId: Long?) : Flow<Response<Boolean>> =
        channelFlow {
            send(Response.Loading)
            if (categoryId != null && transactionId != null) {
                transactionsDao.setTransactionCategory(
                    CategoryTransactionsCrossRef(
                        categoryId,
                        transactionId
                    )
                )
            }
            send(Response.Success(true))
        }

    suspend fun deleteTransaction(loggedTransactionId: Long): Flow<Response<Boolean>> =
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