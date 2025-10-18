package edu.csu.dynamicyouth.network

import android.util.Log
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("DEV","Intercepting ${chain.request().toString()}")

        val token = tokenManager.getExistingToken()

        val response: Response
        val originalRequest = chain.request()
        if (token != null) {
            val requestBuilder = originalRequest.newBuilder()

            token.let {
                requestBuilder.header("Authorization", "Bearer $it")
            }

            response = chain.proceed(requestBuilder.build())
            if (response.code != 401) { //令牌不能用
                return response
            } else {
                response.close()
            }
        }

        synchronized(this) {
            val newToken = runBlocking { tokenManager.getNewToken() }
            if (newToken != null) {
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newToken")
                return chain.proceed(newRequest.build())
            } else {
                //直接中止发送
                chain.call().cancel()
                throw CannotRequireTokenException()
            }
        }

    }
}