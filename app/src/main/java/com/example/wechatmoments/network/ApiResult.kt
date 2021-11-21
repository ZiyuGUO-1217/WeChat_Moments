package com.example.wechatmoments.network

import java.io.IOException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed interface ApiResult {
    data class Success<T>(val data: T) : ApiResult
    data class Error(val errorType: ErrorType) : ApiResult
}

sealed interface ErrorType {
    object NetworkError : ErrorType
    object ServerError : ErrorType
}

@ExperimentalContracts
fun <T> ApiResult.onSuccess(onSuccess: (T) -> Unit): ApiResult {
    if (this.isSuccess()) onSuccess(this.data as T)
    return this
}

@ExperimentalContracts
fun ApiResult.onError(onError: (ErrorType) -> Unit): ApiResult {
    if (this.isError()) onError(this.errorType)
    return this
}

@ExperimentalContracts
fun ApiResult.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is ApiResult.Success<*>)
    }
    return this is ApiResult.Success<*>
}

@ExperimentalContracts
fun ApiResult.isError(): Boolean {
    contract {
        returns(true) implies (this@isError is ApiResult.Error)
    }
    return this is ApiResult.Error
}

fun callApi(block: () -> Unit): ApiResult {
    return try {
        ApiResult.Success(block())
    } catch (e: Exception) {
        if (e is IOException) {
            ApiResult.Error(ErrorType.NetworkError)
        } else {
            ApiResult.Error(ErrorType.ServerError)
        }
    }
}
