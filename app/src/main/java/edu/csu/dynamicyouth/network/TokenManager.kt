package edu.csu.dynamicyouth.network

import android.content.Context
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import retrofit2.Retrofit

@AndroidEntryPoint
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val pureRetrofitUserApi: Retrofit
) {

    suspend fun saveToken(accessToken: String, refreshToken: String) {
        TODO()
    }

    fun getExistingToken(): String? {
        TODO()
    }

    suspend fun getNewToken(): String? {
        TODO("这应当支持在任何界面直接唤起一个登录窗口，这时函数挂起，待登录后再返回")
    }

    fun getRefreshToken(): String? {
        TODO()
    }
}