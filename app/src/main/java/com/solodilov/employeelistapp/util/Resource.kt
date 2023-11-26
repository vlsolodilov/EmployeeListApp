package com.solodilov.employeelistapp.util

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import java.net.UnknownHostException

sealed interface Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>
    data class Error(val errorType: ErrorType) : Resource<Nothing>
    object Loading : Resource<Nothing>
}

enum class ErrorType {
    CONNECTION,
    GENERIC
}

fun <T> Flow<T>.asResult(): Flow<Resource<T>> {
    return this
        .map<T, Resource<T>> { Resource.Success(it) }
        .onStart { emit(Resource.Loading) }
        .catch { exception ->
            Log.d("employeelistapp", "asResult: ${exception.printStackTrace()}")
            val errorType = when (exception) {
                is HttpException, is UnknownHostException -> ErrorType.CONNECTION
                else -> ErrorType.GENERIC
            }
            emit(Resource.Error(errorType))
        }
}
