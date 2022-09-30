package g.sig.core_data

import android.util.Log

sealed interface Response<in T> {
    object Loading : Response<Any?>
    data class Success<T>(val data: T?) : Response<T>
    data class Error<T>(val errorMessage: String?) : Response<T> {
        init {
            if (BuildConfig.DEBUG) Log.d("Error", errorMessage ?: "An unknown error occurred")
        }
    }
}