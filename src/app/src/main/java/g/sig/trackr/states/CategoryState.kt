package g.sig.trackr.states

sealed interface CategoryState {
    data class Error(val errorMessage: String?) : CategoryState
    object Idle: CategoryState
    object Success : CategoryState
    object Loading : CategoryState
}