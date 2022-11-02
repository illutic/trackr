package g.sig.trackr.states

import g.sig.core_data.models.transaction.Category

sealed interface TransactionState {
    data class Idle(val categories: List<Category>) : TransactionState
    object Success : TransactionState
    object Error : TransactionState
    object Loading : TransactionState
}