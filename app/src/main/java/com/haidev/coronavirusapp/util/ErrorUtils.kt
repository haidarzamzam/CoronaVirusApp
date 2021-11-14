package com.haidev.coronavirusapp.util

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

object ErrorUtils {
    fun getErrorThrowableMsg(error: Throwable): String = when (error) {
        is HttpException ->
            when (error.code()) {
                HttpsURLConnection.HTTP_UNAUTHORIZED -> "Unable to access data"
                HttpsURLConnection.HTTP_NOT_FOUND -> "Data not found"
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> "There was a problem with the server"
                HttpsURLConnection.HTTP_BAD_REQUEST -> "Invalid data"
                HttpsURLConnection.HTTP_FORBIDDEN -> "Session has ended"
                else -> "Oops, An error occurred, please try again in a moment"
            }
        is UnknownHostException -> "Unknown Error"
        is ConnectException -> "No internet connected"
        is SocketTimeoutException -> "No internet connected"
        is Errors.OfflineException -> "No internet connected"
        is Errors.FetchException -> "Fetch exception"
        else -> "There is an error"
    }
}