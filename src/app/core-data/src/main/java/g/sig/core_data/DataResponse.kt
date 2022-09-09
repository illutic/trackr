package g.sig.core_data

sealed interface DataResponse<in T> {
    object Loading : DataResponse<Any>
    data class Success<T>(val data: T) : DataResponse<T>
    data class Error<T>(val errorMessage: String?) : DataResponse<T>
}