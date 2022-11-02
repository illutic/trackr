package g.sig.core_data.repositories

import android.content.Context
import g.sig.core_data.Response
import g.sig.core_data.Singletons
import g.sig.core_data.models.transaction.Category
import g.sig.core_data.models.transaction.CategoryTransactions
import g.sig.core_data.models.transaction.MonthCategoriesCrossRef
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest

class CategoryRepo(context: Context) {
    private val categoryDao = Singletons.getDatabase(context).categoryDao()

    suspend fun setCategory(category: Category): Flow<Response<Boolean>> =
        channelFlow {
            send(Response.Loading)
            categoryDao.setCategory(category)
            send(Response.Success(true))
        }

    suspend fun getCategories(): Flow<Response<List<Category>>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(categoryDao.getCategories()))
        }

    suspend fun getCategory(categoryId: Long): Flow<Response<Category>> =
        channelFlow {
            send(Response.Loading)
            val category = categoryDao.getCategory(categoryId)
            if (category == null) send(Response.Error("")) else send(Response.Success(category))
        }

    suspend fun setCategoryMonthRelation(categoryId: Long?, monthId: Long?): Flow<Response<Boolean>> =
        channelFlow {
            send(Response.Loading)
            if (categoryId != null && monthId != null) {
                categoryDao.setCategoryMonthRelation(
                    MonthCategoriesCrossRef(monthId, categoryId)
                )
            }
            send(Response.Success(true))
        }

    suspend fun getCategoryTransactions(categoryId: Long): Flow<Response<CategoryTransactions>> =
        channelFlow {
            send(Response.Loading)
            send(Response.Success(categoryDao.getCategoryTransactions(categoryId)))
        }

    suspend fun deleteCategory(categoryId: Long): Flow<Response<Boolean>> =
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