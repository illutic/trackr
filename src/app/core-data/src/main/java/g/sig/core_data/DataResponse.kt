package g.sig.core_data

sealed interface DataResponse {
    object Loading : DataResponse
    data class Success<T>(val data: T) : DataResponse
    data class Error(val errorMessage: String) : DataResponse
}