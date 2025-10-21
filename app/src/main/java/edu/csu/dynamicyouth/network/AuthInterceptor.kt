package edu.csu.dynamicyouth.network

import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
    private val json: Json
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getExistingToken()

        val response: Response
        val originalRequest = chain.request()
        if (token != null) {
            val requestBuilder = originalRequest.newBuilder()

            token.let {
                requestBuilder.header("Authorization", "Bearer $it")
            }

            response = chain.proceed(requestBuilder.build())

            // 先 peek body 来判断业务码，避免消费掉 response body
            val responseBodyString = response.peekBody(Long.MAX_VALUE).string()
            val code = try {
                json.parseToJsonElement(responseBodyString).jsonObject["code"]?.jsonPrimitive?.int
                    ?: 401
            } catch (_: Exception) {
                // 如果响应不是合法的JSON，则认为是401
                401
            }

            if (code != 401) { //令牌能用
                return response
            } else { //令牌不能用
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