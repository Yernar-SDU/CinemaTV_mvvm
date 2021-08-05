package com.example.cinematv_mvvm.data.cloud

import com.example.cinematv_mvvm.R
import com.example.cinematv_mvvm.core.App
import com.example.cinematv_mvvm.model.response.ErrorResponse
import com.example.cinematv_mvvm.model.response.ErrorStatus
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is UnknownHostException -> {
                    ResultWrapper.Error(
                        ErrorStatus.NO_CONNECTION, null, App.instance.getString(
                            R.string.no_internet_connection
                        )
                    )
                }
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse =
                        convertErrorBody(
                            throwable
                        )
                    ResultWrapper.Error(ErrorStatus.BAD_RESPONSE, code, errorResponse)
                }
                is SocketTimeoutException -> {
                    ResultWrapper.Error(
                        ErrorStatus.TIMEOUT,
                        null,
                        App.instance.getString(R.string.socket_timeout_exception)
                    )
                }
                else -> {
                    ResultWrapper.Error(ErrorStatus.NOT_DEFINED, null, throwable.message)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String? {
    return try {
        throwable.response()?.errorBody()?.string()?.let {
            Gson().fromJson(it, ErrorResponse::class.java).error
        }
    } catch (exception: Exception) {
        null
    }
}