package g.sig.core_data

import g.sig.core_data.models.transaction.Category
import g.sig.core_data.models.transaction.CategoryTransactions
import g.sig.core_data.models.transaction.LoggedTransaction
import g.sig.core_data.models.transaction.MonthCategories
import g.sig.core_data.models.user.UserSettings
import kotlinx.coroutines.flow.Flow

sealed interface DataInterface {
    interface UserInterface : DataInterface {
        suspend fun setUserSettings(userSettings: UserSettings): Flow<DataResponse<Boolean>>
        suspend fun getUserSettings(): Flow<DataResponse<UserSettings>>
    }

    interface MonthInterface : DataInterface {
        suspend fun setBudget(amount: Double): Flow<DataResponse<Boolean>>
        suspend fun getBudget(): Flow<DataResponse<Double>>
        suspend fun getExpenses(): Flow<DataResponse<Double>>
        suspend fun getCategories(monthId: Int): Flow<DataResponse<MonthCategories>>
    }

    interface TransactionInterface : DataInterface {
        suspend fun setTransaction(loggedTransaction: LoggedTransaction): Flow<DataResponse<Boolean>>
        suspend fun deleteTransaction(loggedTransaction: LoggedTransaction): Flow<DataResponse<Boolean>>
    }

    interface CategoryInterface : DataInterface {
        suspend fun setCategory(category: Category): Flow<DataResponse<Boolean>>
        suspend fun getCategories(): Flow<DataResponse<List<Category>>>
        suspend fun getCategoryTransactions(categoryId: Int): Flow<DataResponse<CategoryTransactions>>
        suspend fun deleteCategory(category: Category): Flow<DataResponse<Boolean>>
    }
}