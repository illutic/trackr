package g.sig.trackr.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import g.sig.core_data.Response
import g.sig.core_data.models.transaction.Category
import g.sig.core_data.models.transaction.LoggedTransaction
import g.sig.core_data.repositories.CategoryRepo
import g.sig.core_data.repositories.MonthRepo
import g.sig.core_data.repositories.TransactionsRepo
import g.sig.trackr.states.CategoryState
import g.sig.trackr.states.TransactionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate

class AppViewModel(app: Application) : AndroidViewModel(app) {
    private val categoryRepo by lazy { CategoryRepo(app) }
    private val transactionsRepo by lazy { TransactionsRepo(app) }
    private val monthRepo by lazy { MonthRepo(app) }

    private var _categoryState = MutableStateFlow<CategoryState>(CategoryState.Success)
    val categoryState = _categoryState.asStateFlow()

    private var _transactionState = MutableStateFlow<TransactionState>(TransactionState.Loading)
    val transactionState = _transactionState.asStateFlow()

    fun fetchCategories() {
        viewModelScope.launch {
            categoryRepo.getCategories().collectLatest {
                _categoryState.value = when (it) {
                    is Response.Error -> {
                        _transactionState.value = TransactionState.Error
                        CategoryState.Error(it.errorMessage)
                    }
                    Response.Loading -> {
                        _transactionState.value = TransactionState.Loading
                        CategoryState.Loading
                    }
                    is Response.Success -> {
                        _transactionState.value = TransactionState.Idle(it.data ?: listOf())
                        CategoryState.Idle
                    }
                }
            }
        }
    }

    fun setCategory(category: Category) {
        viewModelScope.launch {
            categoryRepo.setCategory(category).collectLatest {
                _categoryState.value = when (it) {
                    is Response.Error -> CategoryState.Error(it.errorMessage)
                    Response.Loading -> CategoryState.Loading
                    is Response.Success -> CategoryState.Success
                }
            }
        }
    }

    fun addTransaction(
        monthDate: LocalDate,
        loggedTransaction: LoggedTransaction,
        category: Category?
    ) {
        viewModelScope.launch {
            setTransaction(loggedTransaction) { transactionId ->
                setCategoryTransactionRelation(transactionId, category?.categoryId) {
                    getSimpleMonth(monthDate) { monthId ->
                        setCategoryMonthRelation(
                            categoryId = category?.categoryId,
                            monthId = monthId
                        ) {
                            _transactionState.value = TransactionState.Success
                        }
                    }
                }
            }
        }
    }

    private fun setTransaction(
        transaction: LoggedTransaction,
        onSuccess: suspend (Long) -> Unit
    ) {
        viewModelScope.launch {
            transactionsRepo.setTransaction(transaction)
                .collectLatest { transactionResponse ->
                    when (transactionResponse) {
                        is Response.Error -> _transactionState.value = TransactionState.Error
                        Response.Loading -> _transactionState.value = TransactionState.Loading
                        is Response.Success -> onSuccess(
                            transactionResponse.data ?: return@collectLatest
                        )
                    }
                }
        }
    }

    private fun setCategoryTransactionRelation(
        transactionId: Long?,
        categoryId: Long?,
        onSuccess: suspend () -> Unit
    ) {
        viewModelScope.launch {
            transactionsRepo.setTransactionCategoryRelation(transactionId, categoryId)
                .collectLatest {
                    when (it) {
                        is Response.Error -> _transactionState.value = TransactionState.Error
                        Response.Loading -> _transactionState.value = TransactionState.Loading
                        is Response.Success -> if (it.data == true) onSuccess()
                    }
                }
        }
    }

    private fun getSimpleMonth(date: LocalDate, onSuccess: suspend (Long) -> Unit) {
        viewModelScope.launch {
            monthRepo.getSimpleMonth(date)
                .collectLatest { monthResponse ->
                    when (monthResponse) {
                        is Response.Error ->
                            _transactionState.value =
                                TransactionState.Error
                        Response.Loading ->
                            _transactionState.value =
                                TransactionState.Loading
                        is Response.Success -> onSuccess(
                            monthResponse.data?.monthId ?: return@collectLatest
                        )
                    }
                }
        }
    }

    private fun setCategoryMonthRelation(
        categoryId: Long?,
        monthId: Long?,
        onSuccess: suspend () -> Unit
    ) {
        viewModelScope.launch {
            categoryRepo.setCategoryMonthRelation(categoryId, monthId).collectLatest {
                when (it) {
                    is Response.Error -> _transactionState.value =
                        TransactionState.Error
                    Response.Loading -> _transactionState.value =
                        TransactionState.Loading
                    is Response.Success -> onSuccess()
                }
            }
        }
    }

}