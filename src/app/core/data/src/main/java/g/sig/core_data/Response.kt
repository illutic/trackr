package g.sig.core_data

sealed interface Response<in T> {
    object Loading : Response<Any?>
    data class Success<T>(val data: T) : Response<T>
    data class Error<T>(val errorMessage: String?) : Response<T>
}