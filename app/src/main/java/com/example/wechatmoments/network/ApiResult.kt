package com.example.wechatmoments.network

import android.util.Log
import retrofit2.HttpException
import java.io.IOException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val errorType: ErrorType) : ApiResult<Nothing>()
}

sealed interface ErrorType {
    object NetworkError : ErrorType
    object ServerError : ErrorType
}

@ExperimentalContracts
suspend fun <T> ApiResult<T>.onSuccess(action: suspend (T) -> Unit): ApiResult<T> {
    if (this.isSuccess()) action(this.data)
    return this
}

@ExperimentalContracts
suspend fun <T> ApiResult<T>.onError(action: suspend (ErrorType) -> Unit): ApiResult<T> {
    if (this.isError()) action(this.errorType)
    return this
}

@ExperimentalContracts
fun <T> ApiResult<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is ApiResult.Success)
    }
    return this is ApiResult.Success
}

@ExperimentalContracts
fun <T> ApiResult<T>.isError(): Boolean {
    contract {
        returns(true) implies (this@isError is ApiResult.Error)
    }
    return this is ApiResult.Error
}

fun <T> callApi(block: () -> ApiResult<T>): ApiResult<T> {
    return try {
        block()
    } catch (e: Throwable) {
        when (e) {
            is IOException -> ApiResult.Error(ErrorType.NetworkError)
            is HttpException -> {
                Log.d("callApi:", "${e.message}")
                ApiResult.Error(ErrorType.ServerError)
            }
            else -> ApiResult.Error(ErrorType.ServerError)
        }
    }
}
