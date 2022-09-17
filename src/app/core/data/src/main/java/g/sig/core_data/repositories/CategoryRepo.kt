package g.sig.core_data.repositories

import android.content.Context
import g.sig.core_data.DataInterface
import g.sig.core_data.Response
import g.sig.core_data.Singletons
import g.sig.core_data.models.transaction.Category
import g.sig.core_data.models.transaction.CategoryTransactions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

class CategoryRepo(context: Context) : DataInterface.CategoryInterface {
    private val categoryDao = Singletons.getDatabase(context).categoryDao()

    override suspend fun setCategory(category: Category): Flow<Response<Boolean>> =
        channelFlow {
            send(Response.Loading)
            categoryDao.setCategory(category)
            send(Response.Success(true))
        }

    override suspend fun getCategories(): Flow<Response<List<Category>>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(categoryDao.getCategories()))
        }

    override suspend fun getCategory(categoryId: Int): Flow<Response<Category>> =
        channelFlow {
            send(Response.Loading)
            val category = categoryDao.getCategory(categoryId)
            if (category == null) send(Response.Error("")) else send(Response.Success(category))
        }

    override suspend fun getCategoryTransactions(categoryId: Int): Flow<Response<CategoryTransactions>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(categoryDao.getCategoryTransactions(categoryId)))
        }

    override suspend fun deleteCategory(categoryId: Int): Flow<Response<Boolean>> =
        channelFlow {
            getCategory(categoryId).collectLatest {
                when (it) {
                    is Response.Loading -> send(Response.Loading)
                    is Response.Error -> send(Response.Error(it.errorMessage))
                    is Response.Success -> {
                        categoryDao.deleteCategory(it.data)
                        send(Response.Success(true))
                    }
                }
            }
        }
}