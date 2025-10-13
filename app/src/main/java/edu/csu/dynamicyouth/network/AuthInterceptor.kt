package edu.csu.dynamicyouth.network

import jakarta.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getAccessToken()

        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        token.let {
            requestBuilder.header("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}